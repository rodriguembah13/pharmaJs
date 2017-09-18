package com.ballack.com.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.ballack.com.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.ballack.com.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.ballack.com.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.ballack.com.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(com.ballack.com.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(com.ballack.com.domain.Medicament.class.getName(), jcacheConfiguration);
            cm.createCache(com.ballack.com.domain.FamilleMedicament.class.getName(), jcacheConfiguration);
            cm.createCache(com.ballack.com.domain.LigneVente.class.getName(), jcacheConfiguration);
            cm.createCache(com.ballack.com.domain.Fournisseur.class.getName(), jcacheConfiguration);
            cm.createCache(com.ballack.com.domain.Stock.class.getName(), jcacheConfiguration);
            cm.createCache(com.ballack.com.domain.Client.class.getName(), jcacheConfiguration);
            cm.createCache(com.ballack.com.domain.Medicament.class.getName() + ".stocks", jcacheConfiguration);
            cm.createCache(com.ballack.com.domain.Stock.class.getName() + ".medicaments", jcacheConfiguration);
            cm.createCache(com.ballack.com.domain.Vente.class.getName(), jcacheConfiguration);
            cm.createCache(com.ballack.com.domain.Vente.class.getName() + ".ligneVentes", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
