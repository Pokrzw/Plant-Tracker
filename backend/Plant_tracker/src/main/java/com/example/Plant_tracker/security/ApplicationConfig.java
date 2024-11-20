package  com.example.Plant_tracker.security;
import com.example.Plant_tracker.repositories.appUserRepository;


@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final AppUserRepository appUserRepository;

    // @Bean
    // public UserDetailsService userDetailsService() {
    //     return username -> appUserRepository.findByEmail(username)
    //             .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    // }

}