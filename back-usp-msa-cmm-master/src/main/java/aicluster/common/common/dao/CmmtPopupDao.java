package aicluster.common.common.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import aicluster.common.api.popup.dto.PopupGetListParam;
import aicluster.common.common.entity.CmmtPopup;

@Repository
public interface CmmtPopupDao {

	long selectList_count(@Param("param") PopupGetListParam param);

	List<CmmtPopup> selectList(
			@Param("param") PopupGetListParam param,
			@Param("beginRowNum") Long beginRowNum,
			@Param("itemsPerPage") Long itemsPerPage,
			@Param("totalItems") Long totalItems);

	void insert(CmmtPopup popup);

	CmmtPopup select(String popupId);

	void update(CmmtPopup popup);

	void delete(String popupId);

	List<CmmtPopup> selectList_time(
			@Param("systemId") String systemId,
			@Param("dtime") Date dtime);

}
