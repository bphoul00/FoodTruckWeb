var baseURL = new URL(window.location.origin);


var renderActivites = function (activite) {
    createMarker(activite.lieu.lng, activite.lieu.lat);
    mymap.fitBounds(layerMarkers.getBounds());
    return '<li>' + activite.nom + ' &mdash;' + activite.description + '</li>';
};

var renderListeActivites = function (activites) {
    return '<ul>' + activites.map(renderActivites).join('') + '</ul>';
};

var installerListeActivites = function (listeActivitesHtml) {
    document.getElementById('liste-activites').innerHTML = listeActivitesHtml;
};

var fetchActivites = function (url) {
    fetch(url).then(function (resp) {
        return resp.json();
    }).then(function (data) {
        installerListeActivites(renderListeActivites(data));
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
    if (reDate.test(inputDate.value) && testcoordinate(inputLatitude.value, inputDate.value)) {
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
    console.dir(url);
    if (reDate.test(inputDate.value) && testcoordinate(inputLatitude.value, inputDate.value)) {
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
    console.dir(url);
    var xhttp = new XMLHttpRequest();
    xhttp.open("DELETE", url, true);
    xhttp.send();
}
;

var readFormulaire = function () {
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

var readFormulaire2 = function () {
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
        if (requestType() == "post")
        {
            create(inputID, inputNom, inputDescription, inputArrondissement, inputNomLieu, inputLongitude, inputLatitude, inputDate);
        } else if (requestType() == "put")
        {
            update(inputID, inputNom, inputDescription, inputArrondissement, inputNomLieu, inputLongitude, inputLatitude, inputDate);
        } else if (requestType() == "delete")
        {
            deleteById(inputID);
        }

    });
};

document.addEventListener('DOMContentLoaded', function () {
    fetchActivites(new URL('/activites', baseURL));
    readFormulaire2();
    readFormulaire();
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
    console.dir(lat);
    if (lat < -90 || lat > 90) {
        return false;
    } else if (lng < -180 || lng > 180) {
        return false;
    } else {
        return true;
    }
}
