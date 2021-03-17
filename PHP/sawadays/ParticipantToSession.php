
namespace \Entity;
useDoctrine\ORM\MappingasORM;
useSymfony\Component\Validator\ConstraintsasAssert;

@ORM\Entity
@ORM\Table(name="ParticipantToSession")
class ParticipantToSession {

    /***
    @ORM\Column(type="integer")*
    @ORM\Id*
    @ORM\GeneratedValue(strategy="AUTO")
    */
    private $id;

    /***
    @ORM\Column(type="integer")*
    @ORM\Id*
    */
    private $partipantId;

    /***
    @ORM\Column(type="integer")*
    @ORM\Id*
    */
    private $teaMakerSessionId;

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