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
        CD cdInDB = findCD(id);
        if(cdInDB != null){
            return util.getJSONForObject(cdInDB);
        }
        return "{\"message\": \"cd not deleted. cd not found\"}";
    }

    @Override
    public String getCD(String title){
        CD cdInDB = findCD(title);
        if(cdInDB != null){
            return util.getJSONForObject(cdInDB);
        }
        return "{\"message\": \"cd not deleted. cd not found\"}";
    }


    @Override
    public String getAllCDs(){
        Query query = manager.createQuery("Select cd From CD cd");
        Collection<CD> cds = (Collection<CD>) query.getResultList();
        return util.getJSONForObject(cds);
    }

    @Override
    public String createCD(String cd){
        CD aCD = util.getObjectForJSON(cd, CD.class);
        manager.persist(aCD);
        return "{\"message\": \"cd successfully added\"}";
    }

    @Override
    public String deleteCD(Long id){
        CD cdInDB = findCD(id);
        if(cdInDB != null){
            manager.remove(cdInDB);
           return "{\"message\": \"cd successfully deleted\"}";
        }
        return "{\"message\": \"cd not deleted. cd not found\"}";
    }

    @Override
    public String deleteAllCDs(){
        int deletedCount = manager.createQuery("DELETE FROM CD").executeUpdate();
        return "{\"message\": \"$deleteCount cd's deleted\"}";
    }

    @Override
    public String editCD(Long id, String cd){
        CD updatedCD = util.getObjectForJSON(cd, CD.class);
        CD cdInDB = findCD(id);
        if(cdInDB != null){
            updatedCD.setID(cdInDB.getId());
            manager.merge(updatedCD);
            return "{\"message\": \"cd successfully updated\"}";
        }
        return "{\"message\": \"cd not updated. cd not found\"}";
    }

    private CD findCD(Long id){ return manager.find(CD.class, id);}

    private CD findCD(String title){ return manager.find(CD.class, title); }
}
