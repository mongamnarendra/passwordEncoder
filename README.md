# PasswordEncoder

we use spring security to provide security to our website

first we need to disable the default spring secutiy by creating securityconfig class 
  for this we have a class SecurityFilterChain 
 -------------------security config------------------ 
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private MyUserServiceDetails userDetailsService;


 // this is for removing default security provideed by the spring
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(Customizer -> Customizer.disable())                                                                          //CSRF (Cross-Site Request Forgery)
				.authorizeHttpRequests(request -> request.anyRequest().authenticated())
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(Session -> Session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.build();
				
	}

 // this method tells that to use provided data for authentication
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}
}


---------------------------------------------------------------------------------------------------------


in config file we AuthenticationProvider is a inteface so we use DaoAuthenticationProvider ,which implements AuthenticationProvider
This provider needs to properties 
1.password encoder                                ->   provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
2.useerdetailsService


-------------------------------------------------------------------------------------
here we need to provide imlementation for UserDetailsService because it is a interface


@Service
public class MyUserServiceDetails implements UserDetailsService{
	
	@Autowired
	private EmployeeRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee emp = repo.findByName(username);
		
		if(emp==null) {
			throw new UsernameNotFoundException(username);
		}
		
		return new UserPrincipal(emp);
		
	}

}



-------------------------------------------------------------------------------------------
here it the above method we need to return UserDetails but it is also an interface so we need to provide immplementation for this also 
we create a class in model package 

public class UserPrincipal implements UserDetails{

	private Employee e;
	public UserPrincipal(Employee e) {
		this.e=e;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
				return Collections.singleton(new SimpleGrantedAuthority("User"));
	}

	@Override
	public String getPassword() {
		return e.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return e.getName();
	}

}

---------------------------------------------------------------------------------------------------------------
all set
 now we have to implement methods in service layer and also controller for api access

 here we need to encrpyt the password for safety 
we use BCryptPasswordEncoder which uses hashing and we define number of round to be hashed 


public Employee addEmployee(Employee e) {
		e.setPassword(encoder.encode(e.getPassword()));
		return repo.save(e);
	}

 this method is for posting data to the database but before posting data we convert plain password to cipher text
--------------------------------------------------------------------------------------------------------------------
