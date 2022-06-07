package photo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import photo.model.service.PhotoService;

/**
 * Servlet implementation class PhotoListServlet
 */
@WebServlet("/photo/photoList")
public class PhotoListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PhotoService photoService = new PhotoService();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 업무로직
		// 전체게시물수 조회 -> 전체 페이지수
		int totalContent = photoService.getTotalContent();
		int numPerPage = PhotoService.NUM_PER_PAGE;
		int totalPage = (int) Math.ceil((double) totalContent / numPerPage);
		System.out.println("totalContent = " + totalContent + ", totalPage = " + totalPage); // 21 5
		
		// 2. view단 처리
		request.setAttribute("totalPage", totalPage);
		request.getRequestDispatcher("/WEB-INF/views/photo/photoList.jsp")
			.forward(request, response);
	}

}