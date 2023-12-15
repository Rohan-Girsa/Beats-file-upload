package com.atpl.service.impl;

import com.atpl.entity.CompanyUsers;
import com.atpl.entity.Users;
import com.atpl.repo.CompanyUsersRepo;
import com.atpl.repo.UserRepo;
import com.atpl.req.LoginRequest;
import com.atpl.res.JwtResponse;
import com.atpl.service.UserService;
import com.atpl.utility.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Value("${UserRoleId}")
    private int userRoleId;
    @Value("${UserIp}")
    private String userIp;
    @Value("${Password}")
    private String password;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CompanyUsersRepo companyUsersRepo;
    @Autowired
    private Utility utility;

    @Override
    public void saveUsers(String email) {
        try {
            Users u = userRepo.findTopByEmail(email.trim());
            if (u == null) {
                Users user = createNewUser(email.trim());
                userRepo.save(user);
                Users u1 = userRepo.findTopByEmail(email.trim());
                CompanyUsers c = createCompanyUsers(u1.getId());
                companyUsersRepo.save(c);
                log.info("User " + email + " signed up from backend.");
            }
        } catch (Exception e) {
            log.info("Users Exception " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public JwtResponse loginUser(LoginRequest loginRequest) {
        String token = "";
        JwtResponse response = new JwtResponse();
        try {
            Users user = userRepo.findTopByEmail(loginRequest.getEmail());
            if (user != null && utility.matchPassword(user.getPassword(), loginRequest.getPassword())) {
                token = utility.generateToken(user.getEmail());
                response.setStatusCode(200);
                response.setToken(token);
            } else {
                response.setStatusCode(400);
                response.setToken("Invalid Credentials");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatusCode(500);
            response.setToken("Internal Server Error");
        }
        return response;
    }

    private Users createNewUser(String email) {
        Users user = new Users();
        String name = email.substring(0, email.indexOf("@")).replace(".", " ");
        String[] str = name.split(" ");
        String sName = "";
        if (str.length == 1) {
            sName = str[0].substring(0, 2).toUpperCase();
        } else {
            for (String s : str) {
                sName += s.substring(0, 1).toUpperCase();
            }
        }
        String unique = userRepo.genMD5();
        user.setUniqId(unique);
        user.setEmail(email);
        user.setPassword(utility.generateMD5Hash(password));
        user.setName(name);
        user.setShortName(sName);
        user.setIstype(3);
        user.setIsactive(1);
        user.setTimezoneId((short) 49);
        user.setDtCreated(new Date());
        user.setIp(userIp);
        user.setRoleId(userRoleId);
        return user;
    }

    private CompanyUsers createCompanyUsers(int userId) {
        CompanyUsers u = new CompanyUsers();
        String uniq = companyUsersRepo.genUniqId();
        u.setCompanyId(1);
        u.setCompanyUniqueId(uniq);
        u.setUserId(userId);
        u.setUserType(3);
        u.setIsActive(1);
        u.setRoleId(userRoleId);
        u.setActDate(new Date());
        u.setCreated(new Date());
        u.setModified(new Date());
        return u;
    }
}
