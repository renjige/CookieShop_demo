package servlet;

import model.Review;
import service.ReviewService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ReviewServlet", urlPatterns = "/review")
public class ReviewServlet extends HttpServlet {
    private ReviewService reviewService = new ReviewService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int goodsId = Integer.parseInt(request.getParameter("goodsId"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        String comment = request.getParameter("comment");

        Review review = new Review();
        review.setGoodsId(goodsId);
        review.setUserId(userId);
        review.setRating(rating);
        review.setComment(comment);

        reviewService.addReview(review);

        response.sendRedirect("goods_detail.jsp?goodsId=" + goodsId);
    }
}