<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta content="width=device-width, initial-scale=1.0" name="viewport" />

<title>ENJOY TRIP - INDEX</title>
<meta content="" name="description" />
<meta content="" name="keywords" />

<%@ include file="/WEB-INF/views/common/header.jsp"%>

<!-- ======= Hero No Slider Section ======= -->
<section id="hero-no-slider"
   class="masthead d-flex justify-cntent-center align-items-center">
   <div class="container position-relative" data-aos="fade-up"
      data-aos-delay="100">
      <h1 class="mb-1 text-light"
         style="font-size : 70px; text-shadow: -1px 0 #000, 0 1px #000, 1px 0 #000, 0 -1px #000; text-shadow: 1px 1px 1px #000;">
         ENJOY TRIP</h1>
      <h5 class="mb-5 text-light"
         style="font-size : 35px; text-shadow: -1px 0 #000, 0 1px #000, 1px 0 #000, 0 -1px #000; text-shadow: 1px 1px 1px #000;">
         <em>진정한 여행은 새로운 풍경을 보러 가는 것이 아니라,세상을 바라보는 또 하나의 눈을
            얻어오는 것이다.</em>
      </h5>
      <div class="button-box" style="padding-bottom: 20px">
               <br />
            </div>
   </div>
</section>
<!-- End Hero No Slider Section -->
<!-- ======= Portfolio Details Section ======= -->
    <div
      class="modal fade"
      id="contentViewModal"
      data-bs-backdrop="static"
      data-bs-keyboard="false"
      tabindex="-1"
      aria-labelledby="staticBackdropLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-xl">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title"><mark class="purple">여행지 정보</mark></h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body" id="portfolio-details">

         </div>
        </div>
      </div>
    </div>


<main id="main"> <!-- ======= Why Us Section ======= --> <!-- End Why Us Section -->
</main>
<!-- End #main -->

<%@ include file="/WEB-INF/views/common/main_footer.jsp"%>

<script>
      window.onload = function(){
         if (navigator.geolocation) {

              // GeoLocation을 이용해서 접속 위치를 얻어옵니다
              navigator.geolocation.getCurrentPosition(function (position) {
                  var lat = position.coords.latitude; // 위도
                  var lon = position.coords.longitude; // 경도
              
                  console.log(lat);
                  console.log(lon);
              
                  var link = `/attraction/defaultrecommend/\${lat}/\${lon}`;
                  
                  curPlaceTourist(link);
                  });
              } else {
                  lon = 37.5012743,
                      lat = 127.039585;
                      
                  link = `/attraction/defaultrecommend/\${lat}/\${lon}`;
                  
              curPlaceTourist(link);
          }
      }
      
      function curPlaceTourist(link) {
          console.log(link);
          fetch(link)
             .then((response) => response.json())
             .then((data) => makeList(data));
      }

      function makeList(data) {
          let portfolios = document.querySelectorAll(".photo");
          data.forEach(function (place, index){
             if(index >= 4) {return false;}
             console.log(place);
                  let contentId = place.contentId;
                  let contentTitle = place.title;
                  let contentImg = "";
                  if(place.firstImage == ""){
                     contentImg = "https://icon-library.com/images/no-image-icon/no-image-icon-0.jpg";
                  }else{
                     contentImg = place.firstImage;
                  }
                  let contentLink = `/attraction/attractiondetail/\${contentId}`;
                  
                  let aEl = document.createElement("a");
                 let sectionId = "section_"+index;
                 aEl.setAttribute("href", "#"+sectionId);
                 aEl.setAttribute("class", "btn-get-started animate__animated animate__fadeInUp");
                 aEl.setAttribute("style", "text-shadow: -1px 0 #000, 0 1px #000, 1px 0 #000, 0 -1px #000; text-shadow: 1px 1px 1px #000;");
                 aEl.innerText = contentTitle;
                 document.querySelector(".button-box").appendChild(aEl);
                 
                  let section = `
                    <section class="why-us section-bg" data-aos="fade-up" date-aos-delay="200" id=${"${sectionId}"} style="width: 100%; height: 80%;">
                    <div class="container" >
                      <div class="row">
                        <div class="col-lg-6 text-center">
                          <img src="\${contentImg}" alt="" style="width:100%; height:100%"/>
                        </div>

                        <div class="col-lg-6 d-flex flex-column justify-content-center p-5">
                          <div class="icon-box">
                            <h4 class="title modifyViewBtn btn btn-outline-primary btn-sm" onclick="contentView(this);" data-id="\${contentId}">${"${contentTitle}"}</h4> 
                            </div>
                        </div>
                      </div>
                    </div>
                  </section>
                    `;
                    document.querySelector("#main").innerHTML += section;
                });
      }
      
      // 여행지 정보 보기.
         function contentView(el) {
           var modal = new bootstrap.Modal(document.getElementById("contentViewModal"), {
             backdrop: true,
             keyboard: false,
           });
           let contentId = el.getAttribute("data-id");
           console.log(contentId);
            fetch(`/attraction/detail/\${contentId}`)
           .then((response) => response.json())
           .then((data) => detailView(data)); 
            modal.show();
         }
      
         function detailView(data) {
              let tbody = ``;
                tbody += `
                  <div class="container">
                  
                   <div class="row gy-4">
                   <div class="col-lg-8">
                      <div class="portfolio-details-slider swiper">
                         <div class="swiper-wrapper align-items-center">
                            <div class="swiper-slide">
                            `; 
                               if(`${data.firstImage}` != ""){
                               tbody += `
                                  <img
                                     src="https://icon-library.com/images/no-image-icon/no-image-icon-0.jpg"
                                     alt="" />
                                  `;
                               }
                               
                               if(`${data.firstImage}` == ""){
                                  tbody += `
                                  <img src="\${data.firstImage}" alt="" />
                                  `;
                               }
                               tbody += `
                            </div>
                         </div>
                         <!-- <div class="swiper-pagination"></div> -->
                      </div>
                   </div>

                   <div class="col-lg-4">
                      <div class="portfolio-info">
                         <h3>\${data.title}</h3>
                         <ul>
                            <li class="category"><strong>Category</strong>:
                               \${data.contentTypeName}</li>
                            `;
                            if(`${data.addr1}` != ""){
                               tbody += `
                               <li class="address"><strong>Address</strong>:
                                  \${data.addr1} \${data.addr2}</li>
                                  `;
                            }
                            
                            if(`${data.tel}` != ""){
                               tbody += `
                               <li class="tel"><strong>Tel</strong>: \${data.tel}</li>
                               `;
                            }
                            tbody += `
                         </ul>
                      </div>
                      `;
                      if(`${data.overview}` != null){
                         tbody += `
                         <div class="portfolio-description">
                            <h2>Overview</h2>
                            <p>\${data.overview}</p>
                         </div>
                         `;
                      }
                      tbody += `
                   </div>
                </div>
             </div>
                `;
              document.querySelector("#portfolio-details").innerHTML = tbody; 
            
            }
      
    </script> 
</body>
</html>