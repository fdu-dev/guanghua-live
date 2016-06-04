package cn.edu.fudan.live.service;

import java.util.List;

import cn.edu.fudan.live.bean.Demoo;

public interface IDemooService {

	public void addDemoo(Demoo demoo);

	public List<Demoo> getDemooList(int vid);
	
	public List<Demoo> getDemooList(int vid, int max);	

	public void deleteDemooByDidList(List<Integer> didList);
	
	public void deleteDemooByDidListAndUid(List<Integer> didList, int uid);

	public Demoo getDemooByDid(int did);

}
