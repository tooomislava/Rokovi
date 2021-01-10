<?php
namespace tzilic_20;

use DateTime;

/**
 * @Entity @Table(name="rokovi")
 **/
class Rok
{
    /** @Id @Column(type="integer") @GeneratedValue  **/
    private $sifra;

    /** @Column(type="date") **/
    private $datum;

    /** @Column(type="string") **/
	   private $kolegij;

     /** @Column(type="integer") **/
    private $brojPrijavljenih;

	public function __construct($podaci=null)
	{
		if($podaci==null){
			return;
		}
    $date = new DateTime($podaci->datum);
    $this->datum = $date;
		$this->kolegij=$podaci->kolegij;
    $this->brojPrijavljenih=$podaci->brojPrijavljenih;
	}

  public function getSifra(){
  return $this->sifra;
}

public function setSifra($sifra){
  $this->sifra = $sifra;
}

public function getDatum(){
  return $this->datum;
}

public function setDatum($datum){
  $date = new \DateTime($datum);
  $this->datum = $date;
}

public function getKolegij(){
  return $this->kolegij;
}

public function setKolegij($kolegij){
  $this->kolegij = $kolegij;
}

public function getBrojPrijavljenih(){
  return $this->brojPrijavljenih;
}

public function setBrojPrijavljenih($brojPrijavljenih){
  $this->brojPrijavljenih = $brojPrijavljenih;
}

}
