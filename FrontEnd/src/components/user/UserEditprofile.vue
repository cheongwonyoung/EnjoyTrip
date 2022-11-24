<template>
  <div class="mb-5">
    <b-row class="mt-5">
      <b-col></b-col>

      <b-col cols="4">
        <b-form id="register-form">
          <b-row>
            <b-col></b-col>
            <b-col
              cols="6"
              style="text-align: center"
              class="profile-user-img-edit"
            >
              <img :src="preview" class="profile-user-img-edit" />
            </b-col>
            <b-col></b-col>
          </b-row>
          <b-row class="mt-1">
            <b-col></b-col>
            <b-col cols="6" style="text-align: center">
              <div class="filebox">
                <label for="chooseFile">
                  <div class="btn-upload mt-2">프로필 설정</div>
                </label>
                <input
                  ref="preview"
                  @change="uploadImg()"
                  type="file"
                  id="chooseFile"
                  name="chooseFile"
                  accept="image/*"
                />
              </div>

              <!-- <b-input-group-prepend>
                <span
                  ><img
                    :src="require('../../assets/img/info.png')"
                    id="img-navbar"
                /></span>
                <b-input-group-prepend
                  style="margin-left: 10px; font-size: 20px; line-height: 80px"
                >
                  Edit Information
                </b-input-group-prepend></b-input-group-prepend
              > -->
            </b-col>
            <b-col></b-col>
          </b-row>

          <b-input-group class="mt-4">
            <b-input-group-prepend>
              <span class="input-group-text"
                ><font-awesome-icon icon="fa-solid fa-envelope"
              /></span>
            </b-input-group-prepend>
            <b-input-group-prepend class="input-group-text" style="width: auto"
              >ID(email)</b-input-group-prepend
            >
            <b-form-input
              id="input-id"
              v-model="userDto.userId"
              type="email"
              disabled="disabled"
              required
            ></b-form-input>
          </b-input-group>

          <b-input-group class="mt-5">
            <b-input-group-prepend>
              <span class="input-group-text"
                ><font-awesome-icon icon="fa-solid fa-person"
              /></span>
            </b-input-group-prepend>
            <b-input-group-prepend class="input-group-text" style="width: auto"
              >UserName</b-input-group-prepend
            >
            <b-form-input
              id="input-name"
              v-model="userDto.userName"
              type="text"
              required
            ></b-form-input>
          </b-input-group>

          <b-input-group class="mt-5">
            <b-input-group-prepend>
              <span class="input-group-text"
                ><font-awesome-icon icon="fa-solid fa-key"
              /></span>
            </b-input-group-prepend>
            <b-input-group-prepend class="input-group-text" style="width: auto"
              >Password</b-input-group-prepend
            >
            <b-form-input
              id="input-pwd-edit-before"
              v-model="userDto.userPwd"
              type="password"
              placeholder="Enter current Password"
              required
            ></b-form-input>
          </b-input-group>

          <b-input-group class="mt-5">
            <b-input-group-prepend>
              <span class="input-group-text"
                ><font-awesome-icon icon="fa-solid fa-arrows-rotate"
              /></span>
            </b-input-group-prepend>
            <b-input-group-prepend class="input-group-text" style="width: auto"
              >Password</b-input-group-prepend
            >
            <b-form-input
              id="input-pwd-edit-after"
              v-model="changePwd"
              type="password"
              placeholder="Enter Password to change"
              required
            ></b-form-input>
          </b-input-group>

          <div class="mt-5" id="btn-box">
            <b-button
              id="btn-toggle"
              :pressed.sync="myToggle"
              variant="primary"
              @click.prevent="togglePassword"
              >비밀번호 보이기</b-button
            >
            <b-button
              type="button"
              id="btn-editprofile"
              class="btn"
              @click="editProfile"
              >정보 변경 완료</b-button
            >
          </div>
        </b-form>
      </b-col>

      <b-col></b-col>
    </b-row>
  </div>
</template>

<script>
import http from "@/api/http.js";
import { mapState, mapActions } from "vuex";

const memberStore = "memberStore";

export default {
  name: "UserEditprofile",
  data() {
    return {
      userDto: {
        userName: "",
        userId: "",
        userPwd: "",
        profileImg: "",
        saveFolder: "",
      },
      currentPwd: "",
      changePwd: "",
      myToggle: false,
      preview: "",
      file: "",
      image: "",
    };
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
  },
  created() {
    http.get("/user/editprofile/" + this.userInfo.userId).then((data) => {
      console.log(data);
      console.log(this);
      this.userDto.userName = data.data.userInfo.userName;
      this.userDto.userId = data.data.userInfo.userId;
      this.currentPwd = data.data.userInfo.userPwd;
      this.userDto.profileImg = data.data.userInfo.profileImg;
      this.userDto.saveFolder = data.data.userInfo.saveFolder;
      console.log(this.userDto);

      // 초기 설정
      if (this.userDto.profileImg) {
        this.preview =
          "http://localhost:80/upload/file/" +
          this.userDto.saveFolder +
          "/" +
          this.userDto.profileImg;
      } else {
        this.preview = require("../../assets/img/signup.png");
      }
    });
  },
  methods: {
    ...mapActions(memberStore, ["editUserInfo"]),
    togglePassword() {
      let edit_pwd_before = document.querySelector("#input-pwd-edit-before");
      let edit_pwd_after = document.querySelector("#input-pwd-edit-after");
      let btn_toggle_text = document.querySelector("#btn-toggle");
      if (this.myToggle) {
        // 비밀번호 보기 버튼이 눌린다면
        edit_pwd_before.setAttribute("type", "text");
        edit_pwd_after.setAttribute("type", "text");
        btn_toggle_text.innerText = "비밀번호 가리기";
      } else {
        // 비밀번호 가리기 버튼이눌린다면
        edit_pwd_before.setAttribute("type", "password");
        edit_pwd_after.setAttribute("type", "password");
        btn_toggle_text.innerText = "비밀번호 보이기";
      }
    },
    editProfile() {
      if (this.userDto.userPwd == "") {
        alert("현재 패스워드를 반드시 입력하세요.");
        return;
      }
      if (this.userDto.userPwd != this.currentPwd) {
        console.log(this.userDto.userPwd);
        console.log(this.currentPwd);
        alert("비밀번호가 일치하지 않습니다.");
        return;
      } else {
        if (this.changePwd != "") {
          this.userDto.userPwd = this.changePwd;
        }
      }

      const formData = new FormData();
      if (this.$refs["preview"].files);
      console.log("지금 이미지 정보좀 ");
      if (this.$refs["preview"].files[0]) {
        console.log(this.$refs["preview"].files[0]);
        formData.append("profileImg", this.$refs["preview"].files[0]);
      }
      formData.append(
        "userDto",
        new Blob([JSON.stringify(this.userDto)], { type: "application/json" })
      );

      http
        .put("/user/edit", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then(({ data }) => {
          console.log(data);
          this.$router.push({ name: "infoprofile" });
        });

      console.log("지금 찍는거다");
      console.log(this.userDto);
      this.editUserInfo(this.userDto);
    },
    // setProfileImg(file) {
    //   const fileData = (data) => {
    //     this.preview = data;
    //   };
    //   const reader = new FileReader();
    //   reader.readAsDataURL(file);
    //   reader.addEventListener(
    //     "load",
    //     function () {
    //       fileData(reader.result);
    //     },
    //     false
    //   );
    // },
    uploadImg() {
      console.log("이미지 변경되고 여기에 들어왔다");
      var image = this.$refs["preview"].files[0];
      console.log("이미지 이름");
      console.log(image);

      const url = URL.createObjectURL(image);
      this.preview = url;
      this.userDto.saveFolder = this.userDto.userName;
      this.userDto.profileImg = image.name;

      // const url = URL.createObjectURL(image);
    },
  },
};
</script>

<style scope>
#img-navbar {
  width: 100%;
  height: 80px;
}
#profile {
  width: 100%;
  height: 80px;
}
#register-form {
  border: 1px solid lightgray;
  border-radius: 7px;
  padding: 20px;
  text-align: left;
}
#btn-box {
  text-align: center;
}
#btn-toggle {
  color: #1e4356;
  background-color: #ffffff;
  border: 1px solid #1e4356;
  margin-right: 20px;
}
#btn-toggle:hover {
  font-weight: bold;
}
#btn-editprofile {
  color: white;
  background-color: #1e4356;
  margin-left: 20px;
}
#btn-editprofile:hover {
  font-weight: bold;
}

/* 첨부파일 관련 css */
.filebox .upload-name {
  display: inline-block;
  height: 40px;
  padding: 0 10px;
  vertical-align: middle;
  border: 1px solid #dddddd;
  width: 50%;
  color: #999999;
}

.filebox input[type="file"] {
  position: absolute;
  width: 0;
  height: 0;
  padding: 0;
  overflow: hidden;
  border: 0;
}

.btn-upload {
  width: 150px;
  height: 30px;
  background: #fff;
  border: 1px solid rgb(77, 77, 77);
  border-radius: 10px;
  font-weight: 500;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
}
.btn-upload:hover {
  background: rgb(77, 77, 77);
  color: #fff;
}

.profile-user-img-edit {
  width: 90px;
  height: 90px;
  border-radius: 100%;
  overflow: hidden;
}
</style>
