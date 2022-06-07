<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%
	int totalPage = (int) request.getAttribute("totalPage");
%>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/photo.css" />
<section id="photo-wrapper">
	<h2>사진게시판 </h2>
	<% if(loginMember != null) { %>
	<input type="button" value="글쓰기" id="btn-add" 
		onclick="location.href='<%= request.getContextPath() %>/photo/photoEnroll';"/>
	<% } %>
	
	<div id="photo-container"></div>
	<hr />
	<div id='btn-more-container'>
		<button id="btn-more">더보기(<span id="cPage"></span>/<span id="totalPage"><%= totalPage %></span>)</button>
	</div>
</section>
<script>
document.querySelector("#btn-more").onclick = () => {
	const cPageVal = Number(document.querySelector("#cPage").innerText) + 1;
	getPage(cPageVal);
}

const getPage = (cPage) => {
	$.ajax({
		url : "<%= request.getContextPath() %>/photo/morePage",
		data : {cPage},	
		success(resp) {
			console.log(resp);
			
			const container = document.querySelector("#photo-container");
			resp.forEach((photo) => {
				const {no, memberId, content, regDate, renamedFilename} = photo;
				console.log(no, memberId, content, regDate, renamedFilename);
				
				// 이미지 높이 동적계산
				const img = new Image();
				img.src = `<%= request.getContextPath() %>/upload/photo/\${renamedFilename}`;
				img.onload = () => {
				const height = Math.round(img.height * 300 / img.width * 100) / 100;
				
				const div = `
							<div class="polaroid">
								<img src="\${img.src}" alt="" height="\${height}px"/>
								<p class="info">
									<span class="writer">\${memberId}</span>
									<span class="photoDate">\${regDate}</span>
								</p>
								<p class="caption">\${content}</p>
							</div>
							`;
							container.insertAdjacentHTML('beforeend', div); // 자식요소로 맨뒤에추가
				}
			});
		},
		error : console.log,
		// 성공, 실패 상관없이 무조건 처리
		complete(){
			document.querySelector("#cPage").innerHTML = cPage;
			
			// 버튼 비활성화
			if(cPage === <%= totalPage %>){
				const btn = document.querySelector("#btn-more");
				btn.disabled = "disabled"; // 버튼클릭이 잃어나지않게 처리
				btn.style.cursor = "not-allowed";
			}
		}
	});
};

window.addEventListener('load', () => {	
	// 첫페이지 요청
	getPage(1);
});



</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>