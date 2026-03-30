package aicluster.batch.common.dao;

import org.springframework.stereotype.Repository;

import aicluster.batch.common.entity.UsptMvnFc;

@Repository
public interface UsptMvnFcDao {
    UsptMvnFc select(String mvnFcId);
    void update(UsptMvnFc mvnFc);
}
