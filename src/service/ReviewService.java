package service;

import dao.ReviewDao;
import model.Review;

import java.sql.SQLException;
import java.util.List;

public class ReviewService {
    private ReviewDao reviewDao = new ReviewDao();

    public void addReview(Review review) {
        try {
            reviewDao.addReview(review);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Review> getReviewsByGoodsId(int goodsId) {
        try {
            return reviewDao.getReviewsByGoodsId(goodsId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 删除评论
     * @param reviewId 评论 ID
     */
    public void deleteReview(int reviewId) {
        try {
            reviewDao.deleteReview(reviewId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}