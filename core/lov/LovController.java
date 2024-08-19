package com.erp.core.lov;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.log4j.Log4j2;


@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/lov")
public class LovController {

	@Autowired
	private ILovService lovService;

	@Autowired
	private Environment environment;

	@GetMapping("")
	public ResponseEntity<Map<String, List<Lov>>> findAllUiEnabledLov() {
		log.debug("Fetching All Ui Enabled Lovs..");
		try {
			Map<String, List<Lov>> lovDetail = lovService.findAllUiEnabledLov();
			log.debug("Fetched All Ui Enabled Lovs..");
			return new ResponseEntity<Map<String, List<Lov>>>(lovDetail, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error while Fetching All Ui Enabled Lovs.." + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to find Ui Enabled Lov.");
		}
	}

	@GetMapping("/{id}")
//	@PreAuthorize("@securityService.hasPermission('getLov')")
	public ResponseEntity<Lov> getLov(@PathVariable LovPK id) {
		log.debug("Fetching All Lovs with {id}..", id);
		try {
			Lov lov = lovService.getLov(id);
			log.debug("Fetched All Lovs with {id}..", id);
			return new ResponseEntity<Lov>(lov, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error while Fetching All Lovs with {id}..", id + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty("Lov.getLov.invalid.id"));

		}
	}

	@PostMapping("")
	@PreAuthorize("@securityService.hasPermission('addLov')")
	public ResponseEntity<Lov> addLov(@RequestBody Lov lov) {
		log.debug("Adding Lov..");
		try {
			lov = lovService.addLov(lov);
			log.debug("Added Lov..");
			return new ResponseEntity<Lov>(lov, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error while Adding Lov.." + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty("Lov.addLov.invalid.id"));

		}
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("@securityService.hasPermission('updateLov')")
	public ResponseEntity<Lov> deleteLov(@PathVariable LovPK id) {
		log.debug("Deleting Lov with {} ", id);
		try {
			Lov lov = lovService.deleteLov(id);
			log.debug("Deleted Lov with {} ", id);
			return new ResponseEntity<Lov>(lov, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error while Deleting Lov with {} ", id + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					environment.getProperty("Lov.deleteLov.invalid.id"));

		}
	}

	@PutMapping("")
	@PreAuthorize("@securityService.hasPermission('updateLov')")
	public ResponseEntity<Lov> updateLov(@RequestBody Lov lov) {
		log.debug("Updating Lov..");
		try {
			lov = lovService.updateLov(lov);
			log.debug("Updated Lov..");
			return new ResponseEntity<Lov>(lov, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error while Updating Lov.." + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					environment.getProperty("Lov.updateLov.invalid.id"));

		}
	}
}