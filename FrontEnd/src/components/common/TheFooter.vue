<template>
  <div id="footer-box">
    <div class="row">
      <div class="col-lg-6 col-md-6 footer-contact">
        <h4>Contact</h4>
        <p>
          서울특별시 강남구 역삼동 <br />
          테헤란로 212<br />
          <br />

          <strong>형석:</strong> rhfemsqpf90@naver.com<br />
          <strong>원영:</strong> kaka491@naver.com<br />
        </p>
      </div>

      <div class="col-lg-6 col-md-6 footer-info">
        <h3>About Enjoy Trip!!!</h3>
        <p>
          Enjoy Trip에서 제공하는 다양한 관광지 정보를 이용하세요! <br />
          저희의 서비스에 관해 더 알고 싶다면 아래 개발자 링크를 참고하세요
          <br />
        </p>
        <div class="social-links mt-3">
          <div>
            <strong>이형석</strong>&nbsp;&nbsp;
            <a
              href="https://www.instagram.com/h5_lee"
              target="_blank"
              class="dev-link"
              ><i class="fa-brands fa-instagram fa-xl"></i>Instagram</a
            >&nbsp;&nbsp;
            <a href="https://github.com/2HyeongSeok" class="dev-link"
              ><i class="fa-brands fa-github"></i>GitHub</a
            >
          </div>
          <div>
            <strong>정원영</strong>&nbsp;&nbsp;
            <a
              href="https://www.instagram.com/won_yyyyyyy"
              target="_blank"
              class="dev-link"
              ><i class="fa-brands fa-instagram fa-xl"></i>instagram</a
            >&nbsp;&nbsp;

            <a href="https://github.com/cheongwonyoung" class="dev-link"
              ><i class="fa-brands fa-github"></i>GitHub</a
            >
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "TheFooter",
};
</script>

<style>
#footer-box {
  border-top: 3px solid lightgray;
  padding-top: 20px;
  height: 100%;
  position: relative;
  background-color: white;
}
.dev-link {
  color: black;
}
</style>
