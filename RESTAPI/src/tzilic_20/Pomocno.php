<?php
namespace tzilic_20;
use stdClass;

class Pomocno
{
	public static function odgovor($greska, $tekst, $podaci=null)
	{
        $poruka=new stdClass();
        $poruka->greska=$greska;
        $poruka->tekst=$tekst;
        $odgovor=new stdClass();
        $odgovor->poruka=$poruka;
        $odgovor->podaci=$podaci;
        return $odgovor;
	}
}
