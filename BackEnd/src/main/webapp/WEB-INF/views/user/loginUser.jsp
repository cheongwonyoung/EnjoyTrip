<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta content="width=device-width, initial-scale=1.0" name="viewport" />

<title>ENJOY TRIP - LOGIN</title>
<meta content="" name="description" />
<meta content="" name="keywords" />

<%@ include file="/WEB-INF/views/common/header.jsp"%>
<c:if test="${cookie.ssafy_id.value ne null}">
	<c:set var="idck" value="checked"></c:set>
	<c:set var="svid" value="${cookie.ssafy_id.value}"></c:set>
</c:if>

<!-- ======= Hero No Slider Section ======= -->
<main id="main">
	<!-- ======= Contact Section ======= -->
	<section class="breadcrumbs">
		<div class="container">
			<div class="d-flex justify-content-between align-items-center">
				<ol>
					<li><a href="/">Home</a></li>
					<li>로그인</li>
				</ol>
			</div>
			
			<div class="login-page">
				<h2 class="text-center mt-5 mb-3">Welcome to ENJOY TRIP</h2>
				<div class="form">
					<form id="login-form" method="POST" action="">
						<input type="hidden" name="act" value="login">
						<div class="form-check mb-3 float-end">
							<input class="form-check-input"
								style="width: 15px; margin-right: 3px" type="checkbox"
								value="ok" id="saveid" name="saveid" ${idck} /> <label
								class="form-check-label" for="saveid" style="line-height: 30px">
								아이디 저장 </label>
						</div>
						<div class="row" style="height: 66px"></div>
						<div>
							<div class="float-end" id="idcheck-result"
								style="font-size: 15px"></div>
							<label for="userid" class="form-label float-start"
								style="font-size: 15px; margin-left: 3px">아이디: </label> <input
								type="email" class="form-control" id="input-id-login"
								name="userid" placeholder="아이디(이메일)" value="${svid}" />
						</div>
						<div class="mb-3 mt-3">
							<div class="float-end">
								<a id="btn-find-password-modal" class="btn text-danger"
									style="margin-right: 3px; padding-bottom: 3px"
									data-bs-toggle="modal" data-bs-target="#findModal">비밀번호 찾기</a>
							</div>
							<label for="userpwd" class="form-label float-start mt-2"
								style="font-size: 15px; margin-left: 3px;">비밀번호: </label> <input
								type="password" class="form-control" id="input-pwd-login"
								name="userpwd" placeholder="비밀번호" />
						</div>
						<div class="text-danger float-start"
							style="font-size: 15px; margin-left: 3px">${msg}</div>

						<button type="button" id="btn-login" class="btn mt-5"
							style="color: white; background-color: #1E4356">로그인</button>
						<button type="button" id="btn-mv-register" class="btn"
							style="color: white; background-color: #1E4356">회원가입</button>
					</form>
				</div>
			</div>
		</div>
	</section>
</main>

<div class="modal fade" id="findModal" data-bs-backdrop="static"
	data-bs-keyboard="false" tabindex="-1"
	aria-labelledby="staticBackdropLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<!-- Modal Header -->
			<div class="modal-header">
				<h4 class="modal-title">비밀번호 찾기</h4>
				<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
			</div>

			<!-- Modal body -->
			<div class="modal-body">
				<form id="form-delete" method="POST" action="#">
					<input type="hidden" name="act" value="delete">
					<div class="mb-3">
						<label for="question" class="form-label">찾으시는 계정의 아이디를
							입력하세요.</label> <input type="email" class="form-control" id="finduserid"
							placeholder="아이디(이메일)" name="finduserid" />
					</div>
					<div class="mb-3">
						<label for="question" class="form-label">이름을 입력하세요.</label> <input
							type="text" class="form-control" id="findusername"
							placeholder="이름" name="findusername" />
					</div>
					<div class="mb-3">
						<div class="float-start" id="findpassword-result"
							style="font-size: 15px"></div>
						<button type="button" id="btn-find-password" class="btn float-end"
							style="color: white; background-color: #1E4356">찾기</button>
					</div>

				</form>
			</div>

			<!-- Modal footer -->
			<div class="modal-footer">
				<button type="button" class="btn btn-outline-dark"
					data-bs-dismiss="modal">확인</button>
			</div>
		</div>
	</div>
</div>
<!-- End Hero No Slider Section -->

<%@ include file="/WEB-INF/views/common/footer.jsp"%>

<script>
	document.querySelector("#input-id-login").addEventListener(
			"keyup",
			function() {
				let userid = this.value;
				let resultDiv = document.querySelector("#idcheck-result");
				if (!userid.includes("@")) {
					resultDiv.setAttribute("class", "text-danger float-end");
					resultDiv.setAttribute("style",
							"font-size:15px;display:inline-block");
					resultDiv.textContent = "아이디는 '@'를 포함한 Email 형식입니다.";
				}
			});

	document.querySelector("#btn-mv-register").addEventListener("click",
			function() {
				location.href = "/user/regist";
			});

	document.querySelector("#btn-login").addEventListener("click", function() {
		if (!document.querySelector("#input-id-login").value) {
			alert("아이디 입력!!");
			return;
		} else if (!document.querySelector("#input-pwd-login").value) {
			alert("비밀번호 입력!!");
			return;
		} else {
			let form = document.querySelector("#login-form");
			form.setAttribute("action", "/user/login");
			form.submit();
		}
	});
	
	document.querySelector("#btn-find-password").addEventListener("click", function(){
		
		let userid = document.querySelector("#finduserid").value;
		let username = document.querySelector("#findusername").value
    	
		 if (!userid) {
			alert("아이디 입력!!");
			return;
		} else if (!username) {
			alert("이름 입력!!");
			return;
		} else {
	    	let registerinfo = {
	    	    	userId: userid,
	    	        userName: username,
	    	      };
	        let config = {
	                method: "POST",
	                headers: {
	                  "Content-Type": "application/json",
	                },
	                body: JSON.stringify(registerinfo),
	              };
	        
			let resultDiv = document.querySelector("#findpassword-result");
			fetch(`/user/find`, config)
			  .then((response) => response.text())
			  .then((data) => {
					console.log(data);
					if(data) {
			    			   resultDiv.setAttribute("class", "text-primary float-start");
			    		  	   resultDiv.setAttribute("style", "font-size:15px;display:inline-block;margin-left:3px");
			    		       resultDiv.textContent = data;
		    			 }

				});

		}
	});
</script>
</body>
</html>


