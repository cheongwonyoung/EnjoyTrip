<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta content="width=device-width, initial-scale=1.0" name="viewport" />

<title>ENJOY TRIP - EDIT PROFILE</title>
<meta content="" name="description" />
<meta content="" name="keywords" />

<%@ include file="/WEB-INF/views/common/header.jsp"%>

<!-- ======= Hero No Slider Section ======= -->
<main id="main">
	<!-- ======= Contact Section ======= -->
	<section class="breadcrumbs">
		<div class="container">
			<div class="d-flex justify-content-between align-items-center">
				<ol>
					<li><a href="/">Home</a></li>
					<li><a href="/user/mvinfoprofile">마이 페이지</a></li>
					<li>회원 정보 수정</li>
				</ol>
			</div>
			<div class="login-page">
				<h2 class="text-center mt-5 mb-3">회원 정보 수정</h2>
				<div class="form">
					<form class="login-form" id="form-edit" method="POST" action="">
						<input type="hidden" name="act" value="editprofile">
						<div>
							<label for="userid" class="form-label">아이디</label> <input
								type="email" class="form-control" id="input-id-edit"
								name="userid" value="${userInfo.userId}" readonly
								style="color: black; background-color: #ddd5d5; font-weight: bold" />
						</div>
						<div class="mb-5 mt-3">
							<label for="username" class="form-label">이름 </label> <input
								type="text" class="form-control" id="input-name-edit"
								name="username" value="${userInfo.userName}"
								style="color: black;" />
						</div>
						<div class="mb-3 mt-3">
							<label for="userpwd" class="form-label">현재 비밀번호 </label> <input
								type="password" class="form-control" id="input-pwd-edit-before"
								name="userpwd" placeholder="현재 비밀번호는 반드시 입력하세요!"
								style="color: black;" />
						</div>
						<div class="mb-5">
							<label for="userpwd" class="form-label">변경할 비밀번호 </label> <input
								type="password" class="form-control" id="input-pwd-edit-after"
								name="userpwd2" style="color: black;" />
						</div>
						<button type="button" class="btn-pwd-text-changer btn"
							id="btn-pwd-changer-pwd"
							style="color: white; background-color: #1E4356">비밀번호 보기
						</button>
						<button type="button" class="btn-pwd-text-changer"
							id="btn-text-changer-text"
							style="display: none; color: white; background-color: #1E4356">
							비밀번호 가리기</button>
						<button type="button" class="mt-3 btn" id="btn-edit"
							style="color: white; background-color: #1E4356;">정보 변경
							완료</button>
					</form>
				</div>
			</div>
		</div>
	</section>
</main>
<!-- End Hero No Slider Section -->

<%@ include file="/WEB-INF/views/common/footer.jsp"%>
<script>
	let edit_pwd_before = document.querySelector("#input-pwd-edit-before");
	let edit_pwd_after = document.querySelector("#input-pwd-edit-after");
	let pwd_to_text_changer = document.querySelector("#btn-pwd-changer-pwd");
	let text_to_pwd_changer = document.querySelector("#btn-text-changer-text");
	pwd_to_text_changer.addEventListener("click", function() {
		// 비밀번호 보기 버튼이 눌린다면
		edit_pwd_before.setAttribute("type", "text");
		edit_pwd_after.setAttribute("type", "text");
		pwd_to_text_changer.style.display = "none";
		text_to_pwd_changer.style.display = "";
	});
	text_to_pwd_changer.addEventListener("click", function() {
		// 비밀번호 가리기 버튼이눌린다면
		edit_pwd_before.setAttribute("type", "password");
		edit_pwd_after.setAttribute("type", "password");
		pwd_to_text_changer.style.display = "";
		text_to_pwd_changer.style.display = "none";
	});
	
	
	<!-- 수정 -->
	document.querySelector("#btn-edit").addEventListener("click", function(){
	 	let userid = document.querySelector("#input-id-edit").value;
        let pwd = document.querySelector("#input-pwd-edit-before").value;
        let pwd2 = document.querySelector("#input-pwd-edit-after").value;
        let username = document.querySelector("#input-name-edit").value;
		if (!pwd || pwd != `${userInfo.userPwd}`) {
			alert("현재 비밀번호는 반드시 입력하세요!!");
			return;
		}
	       
		if (pwd2 == "") {
			console.log("여기는 안바뀌었을걸?");
			return;				
		}
		
		let modifyinfo = {
          userId: userid,
          userPwd: pwd2,
          userName: username,
        };
        let config = {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(modifyinfo),
        };
        fetch("/user/edit", config)
          .then((response) => location.href="/user/mvinfoprofile");
          /* .then((data) => makeList(data)); */
      
		
	});
</script>
</body>
</html>
