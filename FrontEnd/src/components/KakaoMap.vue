<template>
  <div id="map"></div>
</template>

<script>
export default {
  name: "KakaoMap",
  data() {
    return {
      lat: null,
      lon: null,
      markers: [],
      overlays: [],
    };
  },
  props: {
    recoList: Array,
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
      // var locPosition = new kakao.maps.LatLng(this.lat, this.lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
      //   message = '<div style="padding:5px;">여기에 계신가요?!</div>'; // 인포윈도우에 표시될 내용입니다

      // // 마커와 인포윈도우를 표시합니다
      // this.displayMarker(locPosition, message);
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=915cffed372954b7b44804ed422b9cf0";
      document.head.appendChild(script);
    }
  },
  created() {
    navigator.geolocation.getCurrentPosition((position) => {
      this.lat = position.coords.latitude; // 위도
      this.lon = position.coords.longitude; // 경도
    });

    console.log(this.lat);
  },
  methods: {
    displayMarker(locPosition, message, markerImage) {
      // 마커를 생성합니다
      var marker = new kakao.maps.Marker({
        map: this.map,
        image: markerImage,
        position: locPosition,
      });
      // this.markers.push(marker);
      var iwContent = message, // 인포윈도우에 표시할 내용
        iwRemoveable = true;

      // 인포윈도우를 생성합니다
      var infowindow = new kakao.maps.InfoWindow({
        content: iwContent,
        removable: iwRemoveable,
      });
      console.log("??dfsa");
      // 인포윈도우를 마커위에 표시합니다
      infowindow.open(this.map, marker);

      // 지도 중심좌표를 접속위치로 변경합니다
      this.map.setCenter(locPosition);
    },
    setBounds(bounds) {
      // LatLngBounds 객체에 추가된 좌표들을 기준으로 지도의 범위를 재설정합니다
      // 이때 지도의 중심좌표와 레벨이 변경될 수 있습니다
      this.map.setBounds(bounds);
    },

    setMarkers(map) {
      for (var i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(map);
      }
    },
    hideMarkers() {
      this.setMarkers(null);
    },
    setOverlays(map) {
      for (var i = 0; i < this.overlays.length; i++) {
        this.overlays[i].setMap(map);
      }
    },
    initMarkers() {
      this.markers = [];
    },
    initOverlays() {
      this.overlays = [];
    },
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(this.lat, this.lon),
        level: 5,
      };

      //지도 객체를 등록합니다.
      //지도 객체는 반응형 관리 대상이 아니므로 initMap에서 선언합니다.
      this.map = new kakao.maps.Map(container, options);

      var imageSrc = require("@/assets/img/home.png"); // 마커이미지의 주소입니다
      var imageSize = new kakao.maps.Size(40, 40); // 마커이미지의 크기입니다
      var imageOption = { offset: new kakao.maps.Point(27, 69) }; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

      // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
      var markerImage = new kakao.maps.MarkerImage(
        imageSrc,
        imageSize,
        imageOption
      );

      var locPosition = new kakao.maps.LatLng(this.lat, this.lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
        message = '<div style="padding:5px;">현재위치</div>'; // 인포윈도우에 표시될 내용입니다

      // 마커와 인포윈도우를 표시합니다
      this.displayMarker(locPosition, message, markerImage);
    },
  },
  watch: {
    recoList: {
      deep: true, // 배열 내부까지 본다

      handler(recoList) {
        console.log("???1!!");
        console.log(this.map);
        // let map2 = this.map;
        // let markers2 = [];

        for (var i = 0; i < this.markers.length; i++) {
          this.markers[i].setMap(null);
        }
        // 지도 이동
        var moveLatLon = new kakao.maps.LatLng(
          recoList[0].lattitude,
          recoList[0].longitude
        );
        this.map.panTo(moveLatLon); // 부드럽게 이동(현지도 안에면)

        recoList.forEach((place) => {
          // console.log(Object.keys(place));
          let markerPosition = new kakao.maps.LatLng(
            place.lattitude,
            place.longitude
          );
          let marker = new kakao.maps.Marker({ position: markerPosition });

          marker.setMap(this.map);
          this.markers.push(marker);
          // markers2.push(marker);
          // this.markers.push(new kakao.maps.Marker({ position: marker }));
        });

        // this.markers = markers2;
        // this.markers[0].setMap(this.map);
      },
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#map {
  width: 100%;
  height: 400px;
}

.button-group {
  margin: 10px 0px;
}

button {
  margin: 0 3px;
}
</style>
