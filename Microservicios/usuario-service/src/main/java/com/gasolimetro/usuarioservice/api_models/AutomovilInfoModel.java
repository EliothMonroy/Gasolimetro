package com.gasolimetro.usuarioservice.api_models;

import java.util.List;

public class AutomovilInfoModel {
	private AutomovilModel automovilModel;
	private List<SensorInfoModel> sensoresInfo;
	
	public AutomovilInfoModel() {
		super();
	}

	public AutomovilInfoModel(AutomovilModel automovilModel, List<SensorInfoModel> sensoresInfo) {
		super();
		this.automovilModel = automovilModel;
		this.sensoresInfo = sensoresInfo;
	}

	public AutomovilModel getAutomovilModel() {
		return automovilModel;
	}

	public void setAutomovilModel(AutomovilModel automovilModel) {
		this.automovilModel = automovilModel;
	}

	public List<SensorInfoModel> getSensoresInfo() {
		return sensoresInfo;
	}

	public void setSensoresInfo(List<SensorInfoModel> sensoresInfo) {
		this.sensoresInfo = sensoresInfo;
	}

	@Override
	public String toString() {
		return "AutomovilInfoModel [automovilModel=" + automovilModel + ", sensoresInfo=" + sensoresInfo + "]";
	}
	
}
