package com.woowol.suinCar.controller;


import com.pi4j.io.gpio.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = "/")
	public String home(Model model) throws InterruptedException {
		final GpioController gpio = GpioFactory.getInstance();
		final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_23, "MyLED", PinState.HIGH);
		pin.setShutdownOptions(true, PinState.LOW);
		Thread.sleep(500);
		pin.low();
		Thread.sleep(500);
		pin.toggle();
		Thread.sleep(500);
		pin.toggle();
		Thread.sleep(500);
		pin.pulse(1000, true);
		gpio.shutdown();

		logger.info("hello~!");
		model.addAttribute("msg", "helloWorld");
		return "home";
	}
}

