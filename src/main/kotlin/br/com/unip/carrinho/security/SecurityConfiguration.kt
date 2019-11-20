package br.com.unip.cardapio.security

import br.com.unip.cardapio.security.filter.CorsFilterCustom
import br.com.unip.cardapio.security.filter.JWTAuthenticationFilter
import br.com.unip.cardapio.security.util.TokenUtil
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(val tokenUtil: TokenUtil) :
        WebSecurityConfigurerAdapter() {

    override fun configure(web: WebSecurity?) {
        web!!.ignoring().antMatchers("/autenticar", "/autenticar/**", "/usuarios/**")
    }

    @Throws(Exception::class)
    public override fun configure(http: HttpSecurity) {
        http.cors()
                .and()
                .csrf().disable()

        http.csrf().disable().authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(CorsFilterCustom(), UsernamePasswordAuthenticationFilter::class.java)

        //filtra requisicoes de login
        .addFilterBefore(JWTAuthenticationFilter(tokenUtil), UsernamePasswordAuthenticationFilter::class.java)
    }
}