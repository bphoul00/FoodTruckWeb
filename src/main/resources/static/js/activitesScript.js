var baseURL = new URL(window.location.origin);


var renderActivites = function (activite) {
    createMarker(activite.lieu.lng, activite.lieu.lat);
    mymap.fitBounds(layerMarkers.getBounds());
    return '<tr class=\'clickable-row\' onclick=changeCenter('+activite.lieu.lng+','+activite.lieu.lat+') ><td>'+activite.id+'</td><td>'+activite.nom+'</td><td>'+activite.description +'</td></tr>';
};

var renderListeActivites = function (activites) {
    return '<table class="table"><thead><tr><th>ID</th><th>Nom de l\'activite</th><th>Description de l\'activite</th> </tr></thead><tbody>' + activites.map(renderActivites).join('') + '</tbody></table>';
};

function changeCenter(lat, lng){mymap.setView(new L.LatLng(lat, lng),14)};

var installerListeActivites = function (listeActivitesHtml) {
    document.getElementById('liste-activites').innerHTML = "";
    document.getElementById('liste-activites').innerHTML = listeActivitesHtml;
    console.dir(listeActivitesHtml);
};

var fetchActivites = function (url) {
    fetch(url).then(function (resp) {
        return resp.json();
    }).then(function (data) {
        var a = renderListeActivites(data)
        console.dir(a);
        installerListeActivites(a);
    });
};

function update(inputID, inputNom, inputDescription, inputArrondissement, inputNomLieu, inputLongitude, inputLatitude, inputDate) {
    var url = new URL('/activites-375e', baseURL);
    url.searchParams.append('id', inputID.value);
    url.searchParams.append('nom', inputNom.value);
    url.searchParams.append('description', inputDescription.value);
    url.searchParams.append('arrondissement', inputArrondissement.value);
    url.searchParams.append('nomLieu', inputNomLieu.value);
    url.searchParams.append('lng', inputLongitude.value);
    url.searchParams.append('lat', inputLatitude.value);
    url.searchParams.append('date', inputDate.value);
    if (reDate.test(inputDate.value) && testcoordinate(inputLatitude, inputDate)) {
        var xhttp = new XMLHttpRequest();
        xhttp.open("PUT", url, true);
        xhttp.send();
    } else {
        alert("Valeur incorrecte entrée");
    }
}
;

function create(inputID, inputNom, inputDescription, inputArrondissement, inputNomLieu, inputLongitude, inputLatitude, inputDate) {
    var url = new URL('/activites-375e', baseURL);
    url.searchParams.append('id', inputID.value);
    url.searchParams.append('nom', inputNom.value);
    url.searchParams.append('description', inputDescription.value);
    url.searchParams.append('arrondissement', inputArrondissement.value);
    url.searchParams.append('nomLieu', inputNomLieu.value);
    url.searchParams.append('lng', inputLongitude.value);
    url.searchParams.append('lat', inputLatitude.value);
    url.searchParams.append('date', inputDate.value);
    if (reDate.test(inputDate.value) && testcoordinate(inputLatitude, inputDate)) {
        var xhttp = new XMLHttpRequest();
        xhttp.open("POST", url, true);
        xhttp.send();
    } else {
        alert("Valeur incorrecte entrée");
    }
}
;

function deleteById(inputID) {
    var url = new URL('/activites-375e/' + inputID.value, baseURL);
    var xhttp = new XMLHttpRequest();
    xhttp.open("DELETE", url, true);
    xhttp.send();
    fetchActivites(new URL('/activites', baseURL));

}
;

var search = function (date1,date2) {
  removeMarkers();
  var url = new URL('/activites-375e', baseURL);
  url.searchParams.append('du', date1);
  url.searchParams.append('au', date2);
  console.dir(url);
  fetchActivites(url);
};

var readSearchFormulaire = function () {
    var form = document.getElementById('search-form');
    var startingDate = document.getElementById('inputStartingDate');
    var endingDate = document.getElementById('inputEndingDate');
    form.addEventListener('submit', function (e) {
        e.preventDefault();
        search(startingDate.value, endingDate.value);
    });
};

var requestType = function () {
    var radioButtons = document.getElementsByName("typeOfRequest");
    for (var x = 0; x < radioButtons.length; x++) {
        if (radioButtons[x].checked) {
            return radioButtons[x].value;
        }
    }
}

var readModifyFormulaire = function () {
    var form = document.getElementById('activites-form');
    var inputID = document.getElementById('inputID');
    var inputNom = document.getElementById('inputNom');
    var inputDescription = document.getElementById('inputDescription');
    var inputArrondissement = document.getElementById('inputArrondissement');
    var inputNomLieu = document.getElementById('inputNomLieu');
    var inputLongitude = document.getElementById('inputLongitude');
    var inputLatitude = document.getElementById('inputLatitude');
    var inputDate = document.getElementById('inputDate');
    form.addEventListener('submit', function (e) {
        e.preventDefault();
        if (requestType() === "post")
        {
            create(inputID, inputNom, inputDescription, inputArrondissement, inputNomLieu, inputLongitude, inputLatitude, inputDate);
        } else if (requestType() === "put")
        {
            update(inputID, inputNom, inputDescription, inputArrondissement, inputNomLieu, inputLongitude, inputLatitude, inputDate);
        } else if (requestType() === "delete")
        {
            deleteById(inputID);
        }

    });
};

document.addEventListener('DOMContentLoaded', function () {
    fetchActivites(new URL('/activites', baseURL));
    readModifyFormulaire();
    readSearchFormulaire();
});


//map

var mymap = L.map('mapid').setView([45.509813, -73.569152], 13);

L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
    attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>',
    maxZoom: 18,
    id: 'mapbox.streets',
    accessToken: 'pk.eyJ1Ijoic2NoYW9zZHJhZ29uIiwiYSI6ImNqNWRiMjlibzBqbHcyd25xN3RndHhpb3AifQ.RHntyHgqxaba74qHie71XA'
}).addTo(mymap);

var removeMarkers = function () {
    layerMarkers.clearLayers();
}

//Layer with all Markers
var layerMarkers = new L.FeatureGroup();

//Create marker and add the marker to layer
var createMarker = function (x, y) {
    var point = L.marker([x, y]);
    layerMarkers.addLayer(point);
};

//Add marker layer to the map
mymap.addLayer(layerMarkers);

var reDate = new RegExp("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$");


function testcoordinate(lat, lng) {
    if (lat.value < -90 || lat.value > 90) {
        return false;
    } else if (lng.value < -180 || lng.value > 180) {
        return false;
    } else {
        return true;
    }
}

function idOnly(){
    document.getElementById("inputNom").disabled = true;
    document.getElementById("inputDescription").disabled = true;
    document.getElementById("inputArrondissement").disabled = true;
    document.getElementById("inputNomLieu").disabled = true;
    document.getElementById("inputLongitude").disabled = true;
    document.getElementById("inputLatitude").disabled = true;
    document.getElementById("inputDate").disabled = true;
}

function idNotOnly(){
    document.getElementById("inputNom").disabled = false;
    document.getElementById("inputDescription").disabled = false;
    document.getElementById("inputArrondissement").disabled = false;
    document.getElementById("inputNomLieu").disabled = false;
    document.getElementById("inputLongitude").disabled = false;
    document.getElementById("inputLatitude").disabled = false;
    document.getElementById("inputDate").disabled = false;
}
