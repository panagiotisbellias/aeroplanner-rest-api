package com.projects.aeroplannerrestapi.service.impl;

import com.projects.aeroplannerrestapi.service.TokenBlacklistService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TokenBlacklistServiceImpl implements TokenBlacklistService {

    private static final Log LOG = LogFactory.getLog(TokenBlacklistServiceImpl.class);

    private Set<String> blacklist = new HashSet<>();

    @Override
    public void addToBlacklist(String token) {
        LOG.debug(String.format("addToBlacklist(%s)", token));
        blacklist.add(token);
        LOG.debug(String.format("Token %s is blacklisted", token));
    }

    @Override
    public boolean isBlacklisted(String token) {
        LOG.debug(String.format("isBlacklisted(%s)", token));
        return blacklist.contains(token);
    }
}
