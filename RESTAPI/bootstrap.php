<?php

use Doctrine\ORM\Tools\Setup;
use Doctrine\ORM\EntityManager;

use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Normalizer\DateTimeNormalizer;


class DoctrineBootstrap
{
    private $entityManager;
    private $serializer;
    public function __construct()
    {
        $config = Setup::createAnnotationMetadataConfiguration([],true);
        $conn=[
            'dbname'=>'tzilic_20_20',
            'user'=>'tzilic_20_20',
            'password'=>'FVX8dEyj',
            'host'=>'localhost',
            'driver'=>'pdo_mysql',
            'charset'=>'utf8mb4',
            'driverOptions'=>[1002 => 'set names utf8mb4']
        ];
        $this->entityManager = EntityManager::create($conn,$config);

        $normalizer = array(new DateTimeNormalizer(array('datetime_format' => 'Y-m-d')), new ObjectNormalizer());
        $encoders = [new JsonEncoder()];

        $this->serializer = new Serializer($normalizer,$encoders);

    }

    public function getEntityManager()
    {
        return $this->entityManager;
    }

    public function getJson($podaci)
    {
        return $this->serializer->serialize($podaci,'json');
    }
}
