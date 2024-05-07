var map = new kakao.maps.Map(document.getElementById('map'), {
    center: new kakao.maps.LatLng(37.5642135, 127.0016985),
    level: 3
});
var placeList = document.getElementById('placeList');
var startLatLng, endLatLng;

function searchStart() {
    var keyword = document.getElementById('start').value;
    var ps = new kakao.maps.services.Places();

    ps.keywordSearch(keyword, startPlacesSearchCB);
}

function searchDestination() {
    var keyword = document.getElementById('destination').value;
    var ps = new kakao.maps.services.Places();

    ps.keywordSearch(keyword, destinationPlacesSearchCB);
}

function startPlacesSearchCB(data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {
        placeList.innerHTML = '';

        for (var i = 0; i < data.length; i++) {
            addPlaceToList(data[i], 'start');
        }
    }
}

function destinationPlacesSearchCB(data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {
        placeList.innerHTML = '';

        for (var i = 0; i < data.length; i++) {
            addPlaceToList(data[i], 'destination');
        }
    }
}

function addPlaceToList(place, type) {
    var li = document.createElement('li');
    li.textContent = place.place_name + ' - ' + place.address_name;
    li.addEventListener('click', function () {
        if (type === 'start') {
            startLatLng = new kakao.maps.LatLng(place.y, place.x);
            alert(place.place_name + '를 출발지로 설정하였습니다.');
            displayMarker(startLatLng);
        } else if (type === 'destination') {
            endLatLng = new kakao.maps.LatLng(place.y, place.x);
            alert(place.place_name + '를 도착지로 설정하였습니다.');
            displayMarker(endLatLng);
        }
    });
    placeList.appendChild(li);
}

function displayMarker(position) {
    var marker = new kakao.maps.Marker({
        position: position,
        map: map
    });
    map.setCenter(position);
}

function findRoute() {
    if (!startLatLng || !endLatLng) {
        alert('출발지와 도착지를 설정해주세요.');
        return;
    }

    var startAddress = encodeURIComponent(document.getElementById('start').value);
    var destinationAddress = encodeURIComponent(document.getElementById('destination').value);

    var url = 'https://map.kakao.com/?sName=' + startAddress + '&eName=' + destinationAddress;
    window.open(url);
}

