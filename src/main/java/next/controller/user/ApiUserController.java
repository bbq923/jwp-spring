package next.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import next.dao.UserDao;
import next.model.User;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {
	@Autowired
	UserDao userDao;
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public User signup(@RequestBody User newUser) {
		userDao.insert(newUser);
		return userDao.findByUserId(newUser.getUserId());
	}
}
