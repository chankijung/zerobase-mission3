package zerobase.wifi.service;


import zerobase.wifi.dto.PosHistoryDto;
import zerobase.wifi.model.PosHistoryModel;

import java.util.List;

public class PosHistoryService{

    /**
     * 위경도 조회하는 히스토리 정보에 대한 기능을 구혀내 주세요.

     DB에 저장된 검색 내역 리스트 불러오기

     */

    public List<PosHistoryDto> list(){
        PosHistoryModel posHistoryModel=new PosHistoryModel();
        return posHistoryModel.selectAll();
    }

    public void save(PosHistoryDto posHistoryDto){
        PosHistoryModel posHistoryModel =new PosHistoryModel();
        posHistoryModel.insert(posHistoryDto);
    }




}
