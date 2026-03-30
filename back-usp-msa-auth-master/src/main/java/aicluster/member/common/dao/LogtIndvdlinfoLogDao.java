package aicluster.member.common.dao;

import org.springframework.stereotype.Repository;

import aicluster.member.common.entity.LogtIndvdlinfoLog;

@Repository
public interface LogtIndvdlinfoLogDao {
	void insert(LogtIndvdlinfoLog log);
}
