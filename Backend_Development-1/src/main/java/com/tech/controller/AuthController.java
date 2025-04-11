package com.tech.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.model.Label;
import com.tech.model.User;
import com.tech.service.UserService;
import com.tech.service.labelService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private labelService labelService;
	

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) {
        String name = user.getName();
        String password = user.getPassword();
        String role = user.getRole();

        // Log received input

        if (name == null || password == null || role == null || 
            name.isEmpty() || password.isEmpty() || role.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Missing username, password, or role");
        }

        Optional<User> foundUser = userService.login(name, password, role);

        if (foundUser.isPresent()) {
            return ResponseEntity.ok(foundUser.get());
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials");
        }
    }
	@GetMapping("/label")
	public List<Label> getAllLabels()
	{
		
		  List<Label> allData = labelService.getAllData();
		  System.out.println(allData);
		  return allData;
		
		
	}
		
//	@GetMapping("/labels/{customerId}")
//	public List<Label> getAllLabels(@PathVariable Long cid) {
//		return labelService.getAllLabelsWithLanguages();
//		
//	}
//	
//	
////	@GetMapping("/label/{labelId}")
////	public Label getLabelById(@PathVariable Long labelId) {
////	    return labelService.getLabelById(labelId);
////	}
////	
////	@PutMapping("/label/update/{labelId}")
////	public Label updateTranslation(@PathVariable Long labelId, @RequestBody Map<String, String> updates) {
////	    return labelService.updateTranslation(labelId, updates);
////	}




}
