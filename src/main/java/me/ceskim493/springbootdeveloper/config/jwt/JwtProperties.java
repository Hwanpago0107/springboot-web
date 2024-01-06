package me.ceskim493.springbootdeveloper.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@PropertySource("classpath:/secure.properties")
@ConfigurationProperties("jwt")
public class JwtProperties {
    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.secret-key}")
    private String secretKey;
}
