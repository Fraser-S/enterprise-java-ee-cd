
function isNumberKey(evt)
{
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode != 46 && charCode > 31
        && (charCode < 48 || charCode > 57))
        return false;
    return true;
}


function isNumericKey(evt)
{
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode != 46 && charCode > 31
        && (charCode < 48 || charCode > 57))
        return true;
    return false;
}

function getCdbyId(id) {
    if(id !== null && id !== ""){
        var newid = parseInt(id);
        handleRequest('GET',"http://localhost:8080/CDWebApi/rest/cd/json/"+newid);
    }
    else{
        document.getElementById("para").innerHTML = window.alert("Empty  field. Please enter the CD ID");
    }
}

function getAllCd(){
    handleRequest('GET',"http://localhost:8080/CDWebApi/rest/cd/json/");
}

function deleteAllCd(){
    handleRequest('DELETE',"http://localhost:8080/CDWebApi/rest/cd/json/");
}

function deleteCDbyId(id){
    if(id !== null && id !== ""){
        var newid = parseInt(id);
        handleRequest('DELETE',"http://localhost:8080/CDWebApi/rest/cd/json/"+newid);
    }
    else{
        document.getElementById("para").innerHTML = window.alert("Empty  field. Please enter the CD ID");
    }
}

function addCD(titleName,genre,artistName) {
    if (titleName !== null && titleName !== "" && genre !== null && genre !== "" && artistName !== null && artistName !== "") {

        var cd = '{"title":"' + titleName + '","genre":"' + genre + '","artist":"' + artistName + '"}';
        handleInputRequest('POST', "http://localhost:8080/CDWebApi/rest/cd/json/", cd);
    } else {
        document.getElementById("para").innerHTML = "Can't have empty fields";
    }
}

function editCD(id, titleName,genre,artistName) {
    if(id!=="" && id!==null && titleName !== null && genre !== null && artistName !== null){
        var cd = '{"title":"' + titleName + '","genre":"' + genre + '","artist":"' + artistName + '"}';
        handleInputRequest('PUT', "http://localhost:8080/CDWebApi/rest/cd/json/"+parseInt(id), cd);
    } else {
        document.getElementById("para").innerHTML = "Can't have empty id";
    }
}

    function handleRequest(requestType, requestURL) {
        var request = new XMLHttpRequest();
        request.open(requestType, requestURL);
        request.responseType = 'json';
        request.send();

        request.onload = function () {
            document.getElementById("para").innerHTML = JSON.stringify(request.response);
        }


    }

    function handleInputRequest(requestType, requestURL, data) {
        var request = new XMLHttpRequest();
        request.open(requestType, requestURL);
        request.setRequestHeader('Content-Type', 'application/json');
        request.onload = function(){
            console.log(data);
            document.getElementById("para").innerHTML = JSON.stringify(request.response);
        }
        request.send(data);
    }
