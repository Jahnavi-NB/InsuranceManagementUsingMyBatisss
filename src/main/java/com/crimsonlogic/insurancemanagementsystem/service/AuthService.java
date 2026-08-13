package com.crimsonlogic.insurancemanagementsystem.service;

import java.util.*;
import com.crimsonlogic.insurancemanagementsystem.model.*;
import com.crimsonlogic.insurancemanagementsystem.enums.*;

public interface AuthService {
    Optional<User> login(String username, String password);
}
