/*
 * Gasolimetro.c
 *
 * Created: 03/11/2018 05:19:30 p. m.
 * Author : saidnm
 */ 
#define F_CPU 8000000UL
#define USART_BAUDRATE 9600 // Baud Rate value
#define BAUD_PRESCALE (((F_CPU / (USART_BAUDRATE * 16UL))) - 1) //Modo asyncrono normal UBRR
#include <avr/io.h>
#include <util/delay.h>
#include <avr/interrupt.h>

//PROTOTIPOS DE LAS FUNCIONES A UTILIZAR
void configurarPuertos(); // inicializacion de los puertos
void obtenerFrecuencia(); // obtenemos la frecuencia en pulsos por segundo
void usar_init(); // inicializamos la comunicacion bluetooth
void usar_transmit(int data);
//VARIABLE PARA CONTAR EL NUMERO DE PULSOS
int8_t numPulsos;


ISR(INT0_vect){
	numPulsos++;
	PORTA^=(1<<0);
}
int main(void)
{
	configurarPuertos();
	usar_init();
	char x = 'r';
	usar_transmit(x);
	while (1) 
    {
		//Enviamos datos a traves del modulo bluetooth
		obtenerFrecuencia();
		//usar_transmit(obtenerFrecuencia());
		//usart_msg("ENVIANDO DATOS");    
	}
}
void obtenerFrecuencia(){
	int8_t frecuencia;
	numPulsos = 0;
	//Va la parte del contador de interrupciones
	sei();// Habilita las interrupciones
	_delay_ms(1000); // Esperamos un segundo
	cli();// detenemos las interrupciones
	frecuencia = numPulsos; // HZ (Cantidad de pulsos por segundo)
	//Enviamos los datos
	usar_transmit(frecuencia);
}
void configurarPuertos(){
	// Se configuran los puertos a utilizar
	cli(); // Deshabilita las interrupciones
	DDRA = (1<<0); // Habilitamos el PIN0 del puerto A como salida
	MCUCR |= (1<<ISC01) |(1<<ISC00); //INT0 flanco de subida
	GICR |= (1<<INT0); // INT0 habilitada
}

void usar_init(){
	
	UCSRB |= (1<<RXEN)|(1<<TXEN); // Habilitamos la recepcion y transmision de datos
	UCSRC |= (1<<URSEL)|(1<<USBS)|(3<<UCSZ0); // UCZ0 , UCZ1, UCZ2 indican que se leen 8 bits
	UBRRH = (BAUD_PRESCALE>>8);
	UBRRL = BAUD_PRESCALE;       
	// into the high byte of the UBRR register
}

void usar_transmit(int data){
	//Esperamos a que el buffer de transmision este vacio
	while(!(UCSRA & (1<<UDRE)))
	;
	UDR=data;
}
