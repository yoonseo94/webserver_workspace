<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%
	String memberId = loginMember.getMemberId();
	String password = loginMember.getPassword();
	String memberName = loginMember.getMemberName();
	Date birthday = loginMember.getBirthday(); // null값이어도 input:datetime에서 무시함.
	String email = loginMember.getEmail() != null ? loginMember.getEmail() : "";
	String phone = loginMember.getPhone();
	String address = loginMember.getAddress() != null ? loginMember.getAddress() : "";
	
	String gender = loginMember.getGender() != null ? loginMember.getGender() : "";
	String hobby = loginMember.getHobby(); // 등산,게임

	// "운동,독서".contains("운동")
	List<String> hobbyList = null;
	if(hobby != null){
		String[] arr = hobby.split(",");
		hobbyList = Arrays.asList(arr); // String[] -> List<String>
	}

%>
<section id=enroll-container>
	<h2>회원 정보</h2>
	<form name="memberUpdateFrm" method="post" action="<%= request.getContextPath() %>/member/memberUpdate">
		<table>
			<tr>
				<th>아이디<sup>*</sup></th>
				<td>
					<input type="text" name="memberId" id="memberId_" value="<%= memberId %>" readonly>
				</td>
			</tr>
			<%-- <tr>
				<th>패스워드<sup>*</sup></th>
				<td>
					<input type="password" name="password" id="password" value="<%= password %>" required>
				</td>
			</tr>
			<tr>
				<th>패스워드확인<sup>*</sup></th>
				<td>	
					<input type="password" id="passwordCheck" value="<%= password %>" required><br>
				</td>
			</tr> --%>
			<tr>
				<th>이름<sup>*</sup></th>
				<td>	
				<input type="text"  name="memberName" id="memberName" value="<%= memberName %>"  required><br>
				</td>
			</tr>
			<tr>
				<th>생년월일</th>
				<td>	
				<input type="date" name="birthday" id="birthday" value="<%= birthday %>"><br>
				</td>
			</tr> 
			<tr>
				<th>이메일</th>
				<td>	
					<input type="email" placeholder="abc@xyz.com" name="email" id="email" value="<%= email %>"><br>
				</td>
			</tr>
			<tr>
				<th>휴대폰<sup>*</sup></th>
				<td>	
					<input type="tel" placeholder="(-없이)01012345678" name="phone" id="phone" maxlength="11" value="<%= phone %>" required><br>
				</td>
			</tr>
			<tr>
				<th>주소</th>
				<td>	
					<input type="text" placeholder="" name="address" id="address" value="<%= address %>"><br>
				</td>
			</tr>
			<tr>
				<th>성별 </th>
				<td>
			       		 <input type="radio" name="gender" id="gender0" value="M" <%= "M".equals(gender) ? "checked" : "" %>>
						 <label for="gender0">남</label>
						 <input type="radio" name="gender" id="gender1" value="F" <%= "F".equals(gender) ? "checked" : "" %>>
						 <label for="gender1">여</label>
				</td>
			</tr>
			<tr>
				<th>취미 </th>
				<td>
					<input type="checkbox" name="hobby" id="hobby0" value="운동" <%= hobbyList != null && hobbyList.contains("운동") ? "checked" : "" %>><label for="hobby0">운동</label>
					<input type="checkbox" name="hobby" id="hobby1" value="등산" <%= hobbyList != null && hobbyList.contains("등산") ? "checked" : "" %>><label for="hobby1">등산</label>
					<input type="checkbox" name="hobby" id="hobby2" value="독서" <%= hobbyList != null && hobbyList.contains("독서") ? "checked" : "" %>><label for="hobby2">독서</label><br />
					<input type="checkbox" name="hobby" id="hobby3" value="게임" <%= hobbyList != null && hobbyList.contains("게임") ? "checked" : "" %>><label for="hobby3">게임</label>
					<input type="checkbox" name="hobby" id="hobby4" value="여행" <%= hobbyList != null && hobbyList.contains("여행") ? "checked" : "" %>><label for="hobby4">여행</label><br />
				</td>
			</tr>
		</table>
        <input type="submit" value="정보수정"/>
        <input type="button" value="비밀번호수정" onclick="location.href='<%= request.getContextPath() %>/member/passwordUpdate';"/>
        <input type="button" onclick="deleteMember();" value="탈퇴"/>
	</form>
</section>
<!-- 회원탈퇴폼 : POST /member/memberDelete 전송을 위해 시각화되지 않는 폼태그 이용 -->
<form 
	name="memberDelFrm" 
	action="<%= request.getContextPath() %>/member/memberDelete" 
	method="POST">
	<input type="hidden" name="memberId" value="<%= loginMember.getMemberId() %>" />
</form>
<script>
const deleteMember = () => {
	if(confirm("정말로 탈퇴하시겠습니까?")){
		document.memberDelFrm.submit();
	}
}

/* const updateMember = () => {
	document.memberUpdateFrm.submit(); // form.submit()하는 경우 handler가 호출되지 않는다.
};
 */
/**
 * 회원수정폼 유효성 검사
 */
document.memberUpdateFrm.onsubmit = () => {
	// memberName
	if(!/^[가-힣]{2,}$/.test(memberName.value)){
		alert("이름은 한글 2글자이상 입력해주세요.");
		return false;
	}
	
	// phone
	if(!/^010\d{8}$/.test(phone.value)){
		alert("유효한 전화번호를 입력하세요.");
		return false;
	}
};
</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
