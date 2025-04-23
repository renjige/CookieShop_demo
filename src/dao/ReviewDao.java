package dao;

import model.Review;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import utils.DataSourceUtils;

import java.sql.SQLException;
import java.util.List;

public class ReviewDao {
    public void addReview(Review review) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "INSERT INTO review (goods_id, user_id, rating, comment) VALUES (?,?,?,?)";
        r.update(sql, review.getGoodsId(), review.getUserId(), review.getRating(), review.getComment());
    }

    public List<Review> getReviewsByGoodsId(int goodsId) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "SELECT * FROM review WHERE goods_id = ?";
        return r.query(sql, new BeanListHandler<Review>(Review.class), goodsId);
    }
}