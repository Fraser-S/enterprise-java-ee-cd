package com.qa.cd.business;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.qa.cd.persistence.CD;
import com.qa.cd.util.JSONUtil;

@Stateless
@Default
public class CDServiceDBImpl implements CDService {

    @PersistenceContext(unitName = "primary")
    private EntityManager manager;

    @Inject
    private JSONUtil util;

    @Override
    public String getCD(Long id){
        try{
            CD cdInDB = findCD(id);
            if(cdInDB != null){
                return util.getJSONForObject(cdInDB);
            }
            return "{\"message\": \"cd not deleted. cd not found\"}";
        }catch(Exception exc) {
            return "{\"message\": \"Something went wrong\"}";
        }
    }

    @Override
    public String getAllCDs(){
        try {
            Query query = manager.createQuery("Select cd From CD cd");
            Collection<CD> cds = (Collection<CD>) query.getResultList();
            return util.getJSONForObject(cds);
        }catch(Exception exc) {
            return "{\"message\": \"Something went wrong\"}";
        }
    }

    @Override
    public String createCD(String cd){
        try{
            CD aCD = util.getObjectForJSON(cd, CD.class);

            if(aCD.getTitle() == null)throw new IllegalArgumentException("title is null");
            if(aCD.getGenre() == null)throw new IllegalArgumentException("genre is null");
            if(aCD.getartist() == null)throw new IllegalArgumentException("artist is null");

            manager.persist(aCD);
            return "{\"message\": \"cd successfully added\"}";
        }catch(Exception exc) {
            if(exc instanceof IllegalArgumentException){
                return "{\"message\": \"invalid inputs\"}";
            } else {
                return "{\"message\": \"something went wrong\"}";
            }
        }
    }

    @Override
    public String deleteCD(Long id){
        try{
            CD cdInDB = findCD(id);
            if(cdInDB != null){
                manager.remove(cdInDB);
                return "{\"message\": \"cd successfully deleted\"}";
            }
            return "{\"message\": \"cd not deleted. cd not found\"}";
        }catch(Exception exc) {
            return "{\"message\": \"Something went wrong\"}";
        }
    }

    @Override
    public String deleteAllCDs(){
        try {
            int deletedCount = manager.createQuery("DELETE FROM CD").executeUpdate();
            return "{\"message\": \"$deleteCount cd's deleted\"}";

        }catch(Exception exc) {
            return "{\"message\": \"Something went wrong\"}";
        }
    }

    @Override
    public String editCD(Long id, String cd){
        try{
            CD updatedCD = util.getObjectForJSON(cd, CD.class);
            CD cdInDB = findCD(id);

            if(cdInDB != null){
                if(updatedCD.getTitle() != null && !updatedCD.getTitle().equals("")){ cdInDB.setTitle(updatedCD.getTitle());}
                if(updatedCD.getGenre() != null && !updatedCD.getGenre().equals("") ){ cdInDB.setGenre(updatedCD.getGenre());}
                if(updatedCD.getartist() != null && !updatedCD.getartist().equals("")){ cdInDB.setArtist(updatedCD.getartist());}
                manager.merge(cdInDB);
                return "{\"message\": \"cd successfully updated\"}";
            }
            return "{\"message\": \"cd not updated. cd not found\"}";
        } catch(Exception exc) {
            return "{\"message\": \"Something went wrong\"}";
        }
    }

    private CD findCD(Long id){ return manager.find(CD.class, id);}

    private CD findCD(String title){ return manager.find(CD.class, title); }
}
