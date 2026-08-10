package com.nypunya.enrollment.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain; import javax.servlet.ServletException; import javax.servlet.http.*; import java.io.IOException; import java.nio.charset.StandardCharsets; import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
 private final String secret;
 public JwtAuthenticationFilter(@Value("${security.jwt.secret}") String secret){this.secret=secret;}
 protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain)throws ServletException,IOException{
  String h=req.getHeader("Authorization");
  if(h!=null&&h.startsWith("Bearer ")) try{
   Claims c=Jwts.parser().setSigningKey(secret.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(h.substring(7)).getBody();
   String role=c.get("role",String.class),sub=c.getSubject();
   if(role!=null&&sub!=null&&SecurityContextHolder.getContext().getAuthentication()==null)
    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(sub,null,Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+role))));
  }catch(RuntimeException ignored){}
  chain.doFilter(req,res);
 }
}
