<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en">

<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">

<title>ENJOY TRIP - BOARD</title>
<meta content="" name="description">
<meta content="" name="keywords">

<%@ include file="/WEB-INF/views/common/header.jsp"%>

<main id="main">
	<!-- ======= Our Services Section ======= -->
	<section class="breadcrumbs">
		<div class="container">
			<div class="d-flex justify-content-between align-items-center">
				<h2>일정 공유 목록</h2>
				<!-- 글 작성하기 버튼 -->
				<ol>
					<li><a class="btn"
						style="background-color: #5E88AA; color: white;"
						href="/board/mvwrite"
						>일정 공유하기</a></li>
				</ol>
			</div>

		</div>
	</section>
	<!-- End Our Services Section -->

	<!-- ======= Service Details Section ======= -->
	<section class="service-details pt-4">
		<div class="container">
			<div class="row" id="listPlan"></div>
		</div>
	</section>
	<!-- End Service Details Section -->
</main>
<!-- End #main -->

<%@ include file="/WEB-INF/views/common/footer.jsp"%>

<script>

// 게시글 목록.
fetch(`/board/list`)
  .then((response) => response.json())
  .then((data) => takeContents(data));

//게시글 목록 보기
function takeContents(boards) {
let contents = ``;
boards.forEach(function (board) {
	console.log(board);
	  contents += ` 
	  <div class="col-1 d-flex align-items-stretch" data-aos="fade-up"></div>
   	<div class="col-4 d-flex align-items-stretch" data-aos="fade-up">
       <div class="card" style="width: 100%">
           <div class="card-img"
               style="position: relative; padding-top: 100%; overflow: hidden;">
               <img class="img-thumbnail img-fluid w-100 h-100"
                   style="position: absolute; top: 0; left: 0; right: 0; bottom: 0; max-width: 100%; height: auto;"
                   src="\${board.thumbNail}" alt="...">
           </div>
           <div class="card-body">
               <h5 class="card-title">
                   <a href="/board/mvview/\${board.planId}">\${board.title}</a>
               </h5>
               <div class="read-more float-end">
                   <label style="font-size: 15px">조회수 : \${board.hit}</label>
               </div>
           </div>
       </div>
   </div>
   <div class="col-1 d-flex align-items-stretch" data-aos="fade-up"></div>
   `;
	  
	  
   /*
 tbody += <tr id="view_${board.planId}" class="view text-center" data-id="${user.userId}" ondblclick="userView(this);">
                     <td>${user.userId}</td>
                     <td>${user.userPwd}</td>
                     <td>${user.userName}</td>
                     <td>${user.emailId}@${user.emailDomain}</td>
                     <td>${user.joinDate}</td>
                     <td>
                         <button type="button" class="modifyViewBtn btn btn-outline-primary btn-sm" onclick="viewModify(this);">수정</button>
                         <button type="button" class="deleteBtn btn btn-outline-danger btn-sm" onclick="userDelete(this);">삭제</button>
                     </td>
                 </tr>
                 <tr id="mview_${user.userId}" data-id="${user.userId}" style="display: none;" class="text-center">
                     <td>${user.userId}</td>
                     <td><input type="text" name="userpwd" id="userpwd${user.userId}" value="${user.userPwd}"></td>
                     <td>${user.userName}</td>
                     <td><input type="text" name="email" id="email${user.userId}" value="${user.emailId}@${user.emailDomain}"></td>
                     <td>${user.joinDate}</td>
                     <td>
                         <button type="button" class="modifyBtn btn btn-primary btn-sm" onclick="userModify(this);">수정</button>
                         <button type="button" class="cancelBtn btn btn-danger btn-sm" onclick="closeModify(this);">취소</button>
                     </td>
                 </tr>; */
    }); 
    document.querySelector("#listPlan").innerHTML = contents; 
}

/*

// 회원 등록.
document.querySelector("#btn-join").addEventListener("click", function () {
  let registerinfo = {
    userName: document.querySelector("#username").value,
    userId: document.querySelector("#userid").value,
    userPwd: document.querySelector("#userpwd").value,
    emailId: document.querySelector("#emailid").value,
    emailDomain: document.querySelector("#emaildomain").value,
  };
  let config = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(registerinfo),
  };
  fetch(${root}/admin/user, config)
    .then((response) => response.json())
    .then((data) => makeList(data));
});

//회원 정보 보기.
function userView(el) {
  var modal = new bootstrap.Modal(document.getElementById("userViewModal"), {
    backdrop: true,
    keyboard: false,
  });
  let id = el.getAttribute("data-id");
  fetch(${root}/admin/user/${id})
    .then((response) => response.json())
    .then((data) => {
      document.querySelector("#vid").innerText = data.userId;
      document.querySelector("#vname").innerText = data.userName;
      document.querySelector("#vemail").innerText = data.emailId + "@" + data.emailDomain;
      document.querySelector("#vjoindate").innerText = data.joinDate;
      modal.show();
    });
}

// 회원 정보 수정 보기.
function viewModify(el) {
  let id = el.parentNode.parentNode.getAttribute("data-id");
  document.querySelector(#view_${id}).setAttribute("style", "display: none;");
  document.querySelector(#mview_${id}).setAttribute("style", "display: ;");
}

// 회원 정보 수정 닫기.
function closeModify(el) {
  let id = el.parentNode.parentNode.getAttribute("data-id");
  document.querySelector(#view_${id}).setAttribute("style", "display: ;");
  document.querySelector(#mview_${id}).setAttribute("style", "display: none;");
}

// 회원 정보 수정.
function userModify(el) {
  let id = el.parentNode.parentNode.getAttribute("data-id");
  let pwd = document.querySelector(#userpwd${id}).value;
  let email = document.querySelector(#email${id}).value.split("@");
  let modifyinfo = {
    userId: id,
    userPwd: pwd,
    emailId: email[0],
    emailDomain: email[1],
  };
  let config = {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(modifyinfo),
  };
  fetch(${root}/admin/user, config)
    .then((response) => response.json())
    .then((data) => makeList(data));
}

// 회원 삭제.
function userDelete(el) {
  if (confirm("정말 삭제?")) {
    let id = el.parentNode.parentNode.getAttribute("data-id");
    let config = {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
    };
    fetch(${root}/admin/user/${id}, config)
      .then((response) => response.json())
      .then((data) => makeList(data));
  }
} 

/

/ let modifyViewBtns = document.querySelectorAll(".modifyViewBtn");
        modifyViewBtns.forEach(function(btn) {
            btn.addEventListener("click", function() {
                console.log(btn.parent.parent);
            });
        }); /
/ $(document).on("click", ".modiBtn", function() {
            let mid = $(this).parents("tr").attr("data-id");
            $("#view_" + mid).css("display", "none");
            $("#mview_" + mid).css("display", "");
        }); */
</script>
</body>

</html>

