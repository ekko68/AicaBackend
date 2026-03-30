package aicluster.batch.common.dao;

import org.springframework.stereotype.Repository;

import aicluster.batch.common.entity.UsptMvnCompany;

@Repository
public interface UsptMvnCompanyDao {
    UsptMvnCompany select(String mvnId);
    void update(UsptMvnCompany mvnCmpny);
}
