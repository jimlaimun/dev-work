
namespace \Entity;
useDoctrine\ORM\MappingasORM;
useSymfony\Component\Validator\ConstraintsasAssert;

@ORM\Entity
@ORM\Table(name="TeaMakerSession")
class TeaMakerSession {

    /***
    @ORM\Column(type="integer")*
    @ORM\Id*
    @ORM\GeneratedValue(strategy="AUTO")
    */
    private $id;

    /**
     * Many Users can belong to many sessions and vice versa.
     * @ManyToMany(targetEntity="TeaMakerSession", inversedBy="Partipants")
     * @JoinTable(name="PartipantToSession")
    */
    private $ParticipantToSessionId;

    /***
    @ORM\Column(type="Date")
    *@Assert\NotBlank()
    **/
    private $date;


    /**
     *getters and setters methods
     */
    public function getId()
    {
        return $this->id;
    }

    public function setId($id)
    {
        $this->id = $id
    }

    // etc

}