<%@page import="java.util.List"%>
<%@ page import="board.model.dto.Board, java.util.ArrayList, board.model.dto.Attachment" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%
	List<Board> list = (List<Board>) request.getAttribute("list");
	List<Attachment> attachList = (List<Attachment>) request.getAttribute("attachList");
	String pagebar = (String) request.getAttribute("pagebar");
%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/board.css" />
<section id="board-container">
	<h2>게시판 </h2>
	<table id="tbl-board">
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>첨부파일</th><%--첨부파일이 있는 경우 /images/file.png 표시 width:16px --%>
				<th>조회수</th>
			</tr>
		</thead>
		<tbody>
		<% 
			if(list != null && !list.isEmpty()){
				for(Board board : list) {
		%>
				<tr>
					<td><%= board.getNo() %></td>
					<td><%= board.getTitle() %></td>
					<td><%= board.getMemberId() %></td>
					<td><%= board.getRegDate() %></td>	
				<% 
					if(attachList != null && !attachList.isEmpty()){
						for (Attachment attachment : attachList){ 
							if(board.getNo() == attachment.getNo()){ %>
								<td><img src="<%= request.getContextPath() %>/images/file.png"></td>
							<% }
						}
					} else {%>
					<td></td>
					<% } %>
					
					<td><%= board.getReadCount() %></td>
				</tr>
	<%
					
				}
			}
		
	%>

		</tbody>
	</table>

	<div id='pageBar'>
		<%= pagebar %>
	</div>
</section>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>