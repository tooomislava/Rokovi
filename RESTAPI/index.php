<?php

require 'vendor/autoload.php';
use tzilic_20\Rok;
use tzilic_20\Pomocno;

Flight::route('GET /v2/rokovi', function(){
   $doctrineBootstrap = Flight::db();
   $em = $doctrineBootstrap->getEntityManager();
   $repozitorij=$em->getRepository('tzilic_20\Rok');
   $rokovi = $repozitorij->findAll();
   $podaci = $doctrineBootstrap->getJson($rokovi);
   Flight::json(Pomocno::odgovor(false,'OK',json_decode($podaci)));
});



Flight::route('POST /v2/rok', function(){
  if(strlen(Flight::request()->data->datum)===0){
    Flight::json(Pomocno::odgovor(true,'Datum obavezno',null));
    return;
  }
  if(strlen(Flight::request()->data->kolegij)===0){
    Flight::json(Pomocno::odgovor(true,'Kolegij obavezno',null));
    return;
  }
  if(strlen(Flight::request()->data->brojPrijavljenih)===0){
    Flight::json(Pomocno::odgovor(true,'Broj prijavljenih studenata obavezno',null));
    return;
  }
  $rok = new Rok(Flight::request()->data);
  $doctrineBootstrap = Flight::db();
  $em = $doctrineBootstrap->getEntityManager();
  $em->persist($rok);
  $em->flush();
  $podaci = $doctrineBootstrap->getJson($rok);
  Flight::json(Pomocno::odgovor(false,'OK',[json_decode($podaci)]));
  header("HTTP/1.1 201 Created");
});



Flight::route('PUT /v2/rok/@sifra', function($sifra){
    $doctrineBootstrap = Flight::db();
    $em = $doctrineBootstrap->getEntityManager();
    $repozitorij=$em->getRepository('tzilic_20\Rok');
    $rok = $repozitorij->find($sifra);
    $rok->setDatum(Flight::request()->data->datum);
    $rok->setKolegij(Flight::request()->data->kolegij);
    $rok->setBrojPrijavljenih(Flight::request()->data->brojPrijavljenih);
    $em->persist($rok);
    $em->flush();
    Flight::json(Pomocno::odgovor(false,'OK',null));
  });

  Flight::route('DELETE /v2/rok/@sifra', function($sifra){
    $doctrineBootstrap = Flight::db();
    $em = $doctrineBootstrap->getEntityManager();
    $repozitorij=$em->getRepository('tzilic_20\Rok');
    $rok = $repozitorij->find($sifra);
    $em->remove($rok);
    $em->flush();
    Flight::json(Pomocno::odgovor(false,'OK',[$rok]));
  });

Flight::route('/', function(){
  Flight::json(Pomocno::odgovor(true,'nepotpuni podaci'));
});

Flight::map('notFound', function(){
  Flight::json(Pomocno::odgovor(true,'nepotpuni podaci'));
});

require_once 'bootstrap.php';

Flight::register('db','DoctrineBootstrap');

Flight::start();
