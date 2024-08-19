package com.erp.core.lov;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional
public class LovServiceImpl implements ILovService {

	@Autowired
	private LovRepository lovRepository;

	@Override
	public Lov getLov(LovPK id) {
		LovEntity lovEntity = lovRepository.findLovById(id);
		return new Lov(lovEntity);
	}

	@Override
	public Map<String, List<Lov>> findAllUiEnabledLov() {
		log.debug("Fetch all UiEnabledLov");
		Map<String, List<Lov>> commons = new HashMap<>();
		lovRepository.findAll().forEach(lovEntity -> {
			String type = lovEntity.getId().getType();
			List<Lov> lovs = commons.get(type);
			if (lovs == null) {
				log.debug("Null List");
				lovs = new ArrayList<>();
			}
			lovs.add(new Lov(lovEntity));
			commons.put(type, lovs);
		});
		return commons;
	}

	@Override
	public Lov addLov(Lov lov) {
		log.debug("Add LOV..");
		LovEntity lovEntity = lov.populateLovEntity();
		lovEntity = lovRepository.save(lovEntity);
		return new Lov(lovEntity);

	}

	@Override
	public Lov deleteLov(LovPK id) {
		log.debug("Delete LOV with {} ", id);
		LovEntity lovEntity = lovRepository.findById(id).get();
		lovEntity.setDeleted(true);
		lovEntity = lovRepository.save(lovEntity);
		return new Lov(lovEntity);
	}

	@Override
	public Lov updateLov(Lov lov) {
		log.debug("Update LOV..");
		LovEntity lovEntity = lov.populateLovEntity();
		lovEntity = lovRepository.save(lovEntity);
		return new Lov(lovEntity);
	}

}
