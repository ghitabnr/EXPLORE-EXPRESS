package com.example.demo.Controller;

import com.example.demo.Service.ReservationService;
import com.example.demo.dao.*;
import com.example.demo.repository.GuideRepo;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.ReservRepo;
import com.example.demo.repository.ReviewRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;

import java.nio.file.Path;


import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {
	@Autowired
	private ReservRepo repo;
	@Autowired
	private HotelRepository repos;
	@Autowired
	private ReservationService reservationService;
	@Autowired
	private ReviewRepo reviewRepository;
	@Autowired
	private GuideRepo guideRepo;


	@GetMapping("/")
	    public String home(Model model) {
		 return "index";
	 }
	 @GetMapping("/about")
	 public String about(Model model)
	 {
		 return "about";
	 }
	 @GetMapping("/service")
	 public String service(Model model)
	 {
		 return "service";
	 }
	@GetMapping("/destination")
	public String destination(Model model)
	{
		return "destination";
	}
	 @GetMapping("/packages")
	 public String packages(Model model)
	 {
		 return "packages";
	 }
	 @GetMapping("/booking")
	 public String booking(Model model)
	 {
		 return "booking";
	 }
	@GetMapping("/reservation")
	public String showReservationForm(@RequestParam("hotelId") int hotelId, Model model) {
		// Utilisez hotelId pour effectuer des opérations supplémentaires, telles que récupérer les détails de l'hôtel à partir de la base de données

		// Ajoutez les informations de réservation au modèle pour les afficher dans le formulaire de réservation
		model.addAttribute("hotelId", hotelId);

		Reservation reservation = new Reservation();

		model.addAttribute("reservation", reservation);

		return "index1";
	}

	@PostMapping("/reservation")
	public String submitReservationForm(@ModelAttribute("reservation") Reservation reservation, @RequestParam("hotelId") int hotelId) {
		// Utilisez l'ID de l'hôtel pour récupérer l'objet Hotel à partir de la base de données
		Hotel hotel = repos.findById(hotelId).orElse(null);

		if (hotel == null) {
			// Gérer le cas où l'hôtel n'est pas trouvé
			return "erreur"; // Remplacez "erreur" par le nom de votre vue d'erreur
		}

		// Sauvegardez les informations de réservation dans la base de données, y compris l'objet Hotel
		reservation.setHotel(hotel);
		reservationService.saveReservation(reservation);

		return "Confirmation";
	}
	@GetMapping("/hotels")
	public String hotelList(Model model) {
		List<Hotel> hotels = repos.findAll();
		model.addAttribute("hotels", hotels);
		return "hotels";
	}
	@GetMapping("/detail")
	public String detail(Model model)
	{
		return "HotelDetail";
	}
	@GetMapping("/addHotel")
	public String showCreateHotelsPage(Model model) {
		Hoteldto hotelDto=new Hoteldto();
		model.addAttribute("hotelDto",hotelDto);
		return "AddHotel";
	}

	@PostMapping("/addHotel")
	public String createHotel(@Valid @ModelAttribute Hoteldto hotelDto, BindingResult result) {


		if(hotelDto.getImageFile().isEmpty()) {
			result.addError(new FieldError("hotelDto","imageFile","l'image is required"));
		}
		if(result.hasErrors()) {
			return "AddHotel";
		}
		MultipartFile image=hotelDto.getImageFile();
		Date createdAt=new Date();
		String storageFileName=createdAt.getTime()+"_" +image.getOriginalFilename();
		try {
			String uploadDir = "public/images/";
			Path uploadPath=Paths.get(uploadDir);

			if(!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			try(InputStream inputStream=image.getInputStream()){
				Files.copy(inputStream,Paths.get(uploadDir+storageFileName), StandardCopyOption.REPLACE_EXISTING);
			}
		}catch(Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}


		Hotel hotel=new Hotel();
		hotel.setName(hotelDto.getName());
		hotel.setDescription(hotelDto.getDescription());
		hotel.setAddress(hotelDto.getAddress());
		hotel.setCreatedAt(createdAt);
		hotel.setImages(storageFileName);
		hotel.setPrix(hotelDto.getPrix());

		repos.save(hotel);



		return "hotels"; // Redirige vers la liste des hôtels après l'ajout
	}
	@GetMapping("/search")
	public String searchProduits(@RequestParam("q") String searchTerm, Model model) {
		List<Hotel> hotels = repos.findByNameContaining(searchTerm);
		model.addAttribute("hotels", hotels);
		return "hotels";
	}
	@GetMapping("/contact")
	public String showContact(Model model) {
		Review review=new Review();
		model.addAttribute("review",review);
		return "contact";
	}
	@PostMapping("/contact")
	public String submitReview(@ModelAttribute("Review") Review review, Model model) {
		// Enregistrer la review dans la base de données
		reviewRepository.save(review);
		model.addAttribute("review", new Review());

		// Rediriger vers une page de confirmation ou de remerciement
		return "/contact";
	}
	@GetMapping("/reviews")
	public String reviewList(Model model) {
		List<Review> reviews = reviewRepository.findAll();
		model.addAttribute("reviews", reviews);
		return "index";
	}
	@GetMapping("/addGuide")
	public String showGuide(Model model) {
		GuideDto guide=new GuideDto();
		model.addAttribute("guide",guide);
		return "addGuide";
	}
	@PostMapping("/addGuide")
	public String submitGuide(@Valid@ModelAttribute GuideDto guide,BindingResult result) {
        // Enregistrer la review dans la base de données
        if (guide.getImageFile().isEmpty()) {
            result.addError(new FieldError("guide", "imageFile", "l'image is required"));
        }
        if (result.hasErrors()) {
            return "addGuide";
        }
        MultipartFile image = guide.getImageFile();


        String storageFileName = null;
        try {
            String uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Date createdAt = new Date();
            storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();
            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        Guide guide1 = new Guide();
        guide1.setName(guide.getName());
        guide1.setDesignation(guide.getDesignation());
        guide1.setInstagram(guide.getInstagram());

        guide1.setImages(storageFileName);
        guide1.setContact(guide.getContact());

        guideRepo.save(guide1);


        // Rediriger vers une page de confirmation ou de remerciement
        return "addGuide";
    }
	@GetMapping("/guides")
	public String guideList(Model model) {
		List<Guide> guides = guideRepo.findAll();
		model.addAttribute("guides", guides);
		return "index";
	}
	@GetMapping("/Confirmation")
	public String showConfirmationPage(@ModelAttribute("reservation") Reservation reservation, Model model) {
		// Ajoutez la réservation au modèle pour qu'elle puisse être affichée sur la page
		model.addAttribute("reservation", reservation);
		return "Confirmation"; // Le nom du fichier HTML de confirmation
	}

}
