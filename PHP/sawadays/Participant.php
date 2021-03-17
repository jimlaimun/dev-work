
namespace \Entity;
useDoctrine\ORM\MappingasORM;
useSymfony\Component\Validator\ConstraintsasAssert;

@ORM\Entity
@ORM\Table(name="participant")
class Participant {

    /***
    @ORM\Column(type="integer")*
    @ORM\Id*
    @ORM\GeneratedValue(strategy="AUTO")
    */
    private $id;

    /***
    @ORM\Column(type="string",length=100)
    *@Assert\NotBlank()
    **/
    private $name;
    /***
    @ORM\Column(type="integer")
    *@Assert\NotBlank()
    **/
    private $timeAsMaker;

    /**
     * Many Users can belong to many sessions and vice versa.
     * @ManyToMany(targetEntity="Participant", inversedBy="TeaMakerSession")
     * @JoinTable(name="PartipantToSession")
    */
    private $ParticipantToSessionId;


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