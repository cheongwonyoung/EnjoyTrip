import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "../views/HomeView.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/user",
    name: "user",
    component: () => import("@/views/AppUser"),
    children: [
      {
        path: "regist",
        name: "regist",
        component: () => import("@/components/user/UserRegister"),
      },
      {
        path: "login",
        name: "login",
        component: () => import("@/components/user/UserLogin"),
      },
      {
        path: "infoprofile",
        name: "infoprofile",
        component: () => import("@/components/user/UserInfoprofile"),
      },
      {
        path: "editprofile",
        name: "editprofile",
        component: () => import("@/components/user/UserEditprofile"),
      },
    ],
  },
  {
    path: "/board",
    name: "board",
    component: () => import("@/views/AppBoard"),
    redirect: "/board/list",
    children: [
      {
        path: "list",
        name: "boardlist",
        component: () => import("@/components/board/BoardList"),
      },
      {
        path: "write",
        name: "boardwrite",
        component: () => import("@/components/board/BoardWrite"),
      },
      {
        path: "view/:planid",
        name: "boardview",
        component: () => import("@/components/board/BoardView"),
      },
      // {
      //   path: "modify",
      //   name: "boardmodify",
      //   component: () => import("@/components/board/BoardModify"),
      // },
      // {
      //   path: "delete/:articleno",
      //   name: "boarddelete",
      //   component: () => import("@/components/board/BoardDelete"),
      // },
    ],
  },
  {
    path: "/attraction",
    name: "attraction",
    component: () => import("@/views/AppAttraction"),
  },
  {
    path: "/attractiontop30",
    name: "attractiontop30",
    component: () => import("@/views/AppAttractionTop30"),
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
