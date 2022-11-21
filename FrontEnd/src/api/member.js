import http from "./http.js";

async function login(map, success, fail) {
  console.log("login 메소드 : " + Object.fromEntries(map));
  await http
    .post("/user/login", Object.fromEntries(map))
    .then(success)
    .catch(fail);
}

async function findById(userid, success, fail) {
  http.defaults.headers["access-token"] =
    sessionStorage.getItem("access-token");
  await http.get(`/user/info/${userid}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  http.defaults.headers["refresh-token"] =
    sessionStorage.getItem("refresh-token"); //axios header에 refresh-token 셋팅
  await http.post(`/user/refresh`, user).then(success).catch(fail);
}

async function logout(userid, success, fail) {
  await http.get(`/user/logout/${userid}`).then(success).catch(fail);
}

export { login, findById, tokenRegeneration, logout };
