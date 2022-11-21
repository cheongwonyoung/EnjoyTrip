<template>
  <div id="map"></div>
</template>

<script>
// import http from "@/api/http.js";
export default {
  name: "KakaoMap",
  data() {
    return {
      lat: null,
      lon: null,
      markers: [],
      overlays: [],
      markerInfo: [],
      seletedMarker: null,
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
      script.src = "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=915cffed372954b7b44804ed422b9cf0";
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
      var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

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
        var moveLatLon = new kakao.maps.LatLng(recoList[0].lattitude, recoList[0].longitude);
        this.map.panTo(moveLatLon); // 부드럽게 이동(현지도 안에면)

        console.log("test");
        console.log(recoList);
        recoList.forEach((place) => {
          // console.log(Object.keys(place));
          let markerPosition = new kakao.maps.LatLng(place.lattitude, place.longitude);

          // var iwContent;

          let marker = new kakao.maps.Marker({
            map: this.map,
            position: markerPosition,
            clickable: true,
          });
          console.log(place);
          // var iwRemoveable = true;
          var content =
            '<div class="wrap">' +
            '    <div class="info">' +
            '        <div class="title">' +
            `            ${place.title}` +
            '            <div class="close map-close" onclick="closeOverlay()" title="닫기"></div>' +
            "        </div>" +
            '        <div class="body">' +
            '            <div class="img">' +
            `                <img src=${place.firstImage} width="73" height="70">` +
            "           </div>" +
            '            <div class="desc">' +
            `                <div class="ellipsis">${place.addr1}</div>` +
            `                <div class="jibun ellipsis">${place.tel}</div>` +
            `                <div><a href="https://map.naver.com/v5/search/${place.title}" target="_blank" class="link"><i class="fa-solid fa-map-location-dot fa-xl"></i> 지도</a>
            <a href="https://www.instagram.com/explore/tags/${place.title}" target="_blank" class="link"><i class="fa-brands fa-instagram fa-xl"></i>인스타그램</a></div>` +
            "            </div>" +
            "        </div>" +
            "    </div>" +
            "</div>";

          // var infowindow = new kakao.maps.InfoWindow({
          //   content: content,
          //   // map: this.map,
          //   // position: marker.getPosition(),
          //   removable: iwRemoveable,
          // });

          // 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
          kakao.maps.event.addListener(marker, "click", () => {
            // overlay.setMap(this.map);
            var overlay = new kakao.maps.CustomOverlay({
              content: content,
              map: this.map,
              position: marker.getPosition(),
            });
            this.setOverlays(null);

            if (!this.seletedMarker || this.seletedMarker !== marker) {
              // 클릭된 마커 객체가 null이 아니면
              // 클릭된 마커의 커스텀 오버레이를 닫고

              // 생성된 오버레이를 배열에 추가합니다
              this.overlays.push(overlay);

              // 현재 클릭된 마커의 커스텀 오버레이를 띄운다
              overlay.setMap(this.map);

              var closeBtn = document.querySelector(".map-close");
              closeBtn.addEventListener("click", () => {
                overlay.setMap(null);
              });
            }

            this.seletedMarker = marker;

            // infowindow.open(this.map, marker);
          });

          marker.setMap(this.map);
          this.markers.push(marker);
        });
      },
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
#map {
  width: 100%;
  height: 400px;
}
.wrap {
  position: absolute;
  left: 0;
  bottom: 40px;
  width: 288px;
  height: 132px;
  margin-left: -144px;
  text-align: left;
  overflow: hidden;
  font-size: 12px;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  line-height: 1.5;
}
.wrap * {
  padding: 0;
  margin: 0;
}

.wrap .info {
  width: 286px;
  height: 120px;
  border-radius: 5px;
  border-bottom: 2px solid #ccc;
  border-right: 1px solid #ccc;
  overflow: hidden;
  background: #fff;
}
.wrap .info:nth-child(1) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.info .title {
  padding: 5px 0 0 10px;
  height: 30px;
  background: #eee;
  border-bottom: 1px solid #ddd;
  font-size: 18px;
  font-weight: bold;
}
.info .close {
  position: absolute;
  top: 10px;
  right: 10px;
  color: #888;
  width: 17px;
  height: 17px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png");
}
.info .close:hover {
  cursor: pointer;
}
.info .body {
  position: relative;
  overflow: hidden;
}
.info .desc {
  position: relative;
  margin: 13px 0 0 90px;
  height: 75px;
}
.desc .ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.desc .jibun {
  font-size: 11px;
  color: #888;
  margin-top: -2px;
}
.info .img {
  position: absolute;
  top: 6px;
  left: 5px;
  width: 73px;
  height: 71px;
  border: 1px solid #ddd;
  color: #888;
  overflow: hidden;
}
.info:after {
  content: "";
  position: absolute;
  margin-left: -12px;
  left: 50%;
  bottom: 0;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.info .link {
  color: #5085bb;
}
</style>
