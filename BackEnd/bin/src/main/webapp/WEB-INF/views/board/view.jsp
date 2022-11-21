<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
	<section class="breadcrumbs">
		<div class="container">
			<div class="d-flex justify-content-between align-items-center">
				<h2>일정 공유하기</h2>
				<ol>
					<li><a href="/">Home</a></li>
					<li><a href="/board/mvlist">일정 공유</a></li>
					<li>일정 상세 보기</li>
				</ol>
			</div>
		</div>
	</section>
	<!-- End Our Services Section -->

	<!-- ======= Services Section ======= -->
	<section class="services">
		<div class="container" id="viewPart"></div>
	</section>
	<!-- End Services Section -->

</main>
<!-- End #main -->

<%@ include file="/WEB-INF/views/common/footer.jsp"%>
<script>
	let pId = "${planId}";
	fetch(`/board/view/\${pId}`)
          .then((response) => response.json())
          .then((data) => makeList(data));
	
	function makeList(views){
		let contents = ``;
		
		contents += `
		<div class="row mb-3">
			<h2>\${views.title}</h2>
		</div>
		<div class="row">
			<div class="col-md-8">
				<div class="clearfix align-content-center">
					<p>
						<span class="fw-bold">${userInfo.userName}</span> <br /><span
							class="text-secondary fw-light"> \${views.registerDate}
						</span>
					</p>
				</div>
			</div>
			<div class="col-md-4 align-self-center text-end">조회 :
				\${views.hit + 1}</div>
		</div>
		<div class="row mt-3"> `;

		console.log(views.contentList.length);
		if(views.contentList.length == 1){
			<!-- 1개 있을 때 -->
			contents += `
			<div class="col-4 d-flex align-items-stretche data-aos="fade-up""></div> `;
			views.contentList.forEach(function (content){
				contents += `
				<div class="col-4 align-items-stretche data-aos="fade-up">
					<div class="icon-box icon-box-pink">
						<div>
							<img src="\${content.contentImg}"
								style="width: 100%; height: 200px">
						</div>
						<h4 class="title" style="margin-top: 30px">
							<a href="">\${content.contentTitle}</a>
						</h4>
						<p class="description" style="height: 60px">\${content.contentDesc}</p>
					</div> 
				</div>`;
			});
		}else if(views.contentList.length == 2){
			<!-- 2개 있을 때 -->
			contents += `
			<div class="col-2 d-flex align-items-stretche data-aos="fade-up""></div> `;
			views.contentList.forEach(function (content){
				contents += `
				<div class="col-4 align-items-stretche data-aos="fade-up">
					<div class="icon-box icon-box-pink">
						<div>
							<img src="\${content.contentImg}"
								style="width: 100%; height: 200px">
						</div>
						<h4 class="title" style="margin-top: 30px">
							<a href="">\${content.contentTitle}</a>
						</h4>
						<p class="description" style="height: 60px">\${content.contentDesc}</p>
					</div>
				</div> `;
			});
		}else if(views.contentList.length == 3){
			<!-- 3개 있을 때 -->
			views.contentList.forEach(function (content){
				contents += `
				<div class="col-4 align-items-stretche data-aos="fade-up">
					<div class="icon-box icon-box-pink">
						<div>
							<img src="\${content.contentImg}"
								style="width: 100%; height: 200px">
						</div>
						<h4 class="title" style="margin-top: 30px">
							<a href="">\${content.contentTitle}</a>
						</h4>
						<p class="description" style="height: 60px">\${content.contentDesc}</p>
					</div>
				</div> `;
			});
		}else{
			<!-- 4개 있을 때 -->
			views.contentList.forEach(function (content){
				contents += `
				<div class="col-3 align-items-stretche data-aos="fade-up">
					<div class="icon-box icon-box-pink">
						<div>
							<img src="\${content.contentImg}"
								style="width: 100%; height: 200px">
						</div>
						<h4 class="title" style="margin-top: 30px">
							<a href="">\${content.contentTitle}</a>
						</h4>
						<p class="description" style="height: 60px">\${content.contentDesc}</p>
					</div>
				</div> `;
			});
		}
		
		contents += `
			</div>
			<div class="row mb-3">
				<div class="col-md-8"></div>
				<div class="col-md-4 align-self-center text-end">
					<a class="btn" style="background-color: #5E88AA; color: white"
						href="/board/mvlist">목록보기</a> `;
			<!-- 본인 글이면 보이게끔 -->
			<!-- 여기 해야함 -->
			if(views.userId == `${userInfo.userId}`){
				contents += `
				<a class="btn btn-danger" id="btn-delete-view" onclick="deleteView();">삭제하기</a> `;

			}
			contents += `
				</div>
			</div> `;
		
		
	
		document.querySelector("#viewPart").innerHTML = contents; 
	}
	
	function deleteView(){
		if (confirm("정말로 삭제하시겠습니까?")) {
	        // 확인(예) 버튼 클릭 시 이벤트
	        let config = {
    	          method: "DELETE",
    	          headers: {
    	            "Content-Type": "application/json",
    	          },
    	        };
	        fetch(`/board/list/\${pId}`, config)
	        .then((response) => location.href = "/board/mvlist")
	    }
	}
	
	
	

</script>
</body>

</html>