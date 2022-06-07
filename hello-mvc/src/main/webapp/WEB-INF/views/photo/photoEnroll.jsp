<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>    
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/photo.css" />
<section id="photo-wrapper">
<h2>사진게시판 작성</h2>
<form action="<%=request.getContextPath() %>/photo/photoEnroll" method="post" enctype="multipart/form-data">
    <table id="tbl-photo">
    <tr>
        <th>작성자</th>
        <td><input type="text" name="memberId" value="<%= loginMember.getMemberId() %>" readonly required/></td>
    </tr>
    <tr>
        <th>첨부파일</th>
        <td><input type="file" name="upFile" accept="image/*" onchange="loadImage(this);" required></td>
    </tr>
    <tr>
        <th>이미지 보기</th>
        <td>
            <div id="img-viewer-container">
                <img id="img-viewer" width="350px">
            </div>
        </td>
    </tr>
    <tr>
        <th>내 용</th>
        <td><textarea rows="5" cols="50" name="content"></textarea></td>
    </tr>
    <tr>
        <th colspan="2">
            <input type="submit" value="등록하기">
        </th>
    </tr>
</table>
</form>
</section>

<script>
const loadImage = (input) => {
	console.log(input.files); 
	if(input.files[0]){
		const fr = new FileReader(); // 파일리더객체생성
		fr.readAsDataURL(input.files[0]); // 파일읽어오라고 시킴
		fr.onload = (e) => { // 다읽어내면 핸들러
			document.querySelector("#img-viewer").src = e.target.result;//이진파일을 data url처리값
		}
	}
}
</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>