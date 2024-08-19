package com.erp.core.lov;

import java.util.List;
import java.util.Map;

public interface ILovService {

	Lov getLov(LovPK id);

	Lov addLov(Lov lov);

	Lov deleteLov(LovPK id);

	Lov updateLov(Lov lov);

	Map<String, List<Lov>> findAllUiEnabledLov();

}
