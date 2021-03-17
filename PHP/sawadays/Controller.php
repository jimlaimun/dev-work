use \Entity\Participant;
use \Form\ParticipantForm;
use \Entity TeaMakerSession
use \Entity ParticipantToSession

class ParticipantController extend <some controller class>
{

    /*
    * get random partipant
    * @Rest\Get("/user")
    */
    public function getteaMaker(Request $request)
    {
        $sessionId  = $request->query->get('sessionId');
        $session = $this->getDoctrine()->getRepository('ParticipantToSession:class')->find($sessionId);
        if($session == null){
            throw new ApiException('session not found');
        }

        $users = $this->getDoctrine()->getRepository('Participant:class')->find($sessionId);

        // $minResult = get query to find min time being tea get tea maker for given users
        if(count($minResult) ==1)
        {
            minResult ->setTimeAsMaker($minResult->getTimeAsMaker() +1)
            $em=$this->getDoctrine()->getManager();
            $em->persist($minResult);
            $em->flush();
            return$this->handleView($this->view($minResult -> getName()));
        }else{
            $user = $minresult
        }

        $selected = false;
        $selectedUser = null;
        while(!selected)
        {
            $user = array_rand($users,1)
            user ->setTimeAsMaker(user->getTimeAsMaker() +1)
            $em=$this->getDoctrine()->getManager();
            $em->persist($user);
            $em->flush();
            $selected = true;
            $selectedUser = $user
        }
        return$this->handleView($this->view($selectedUser -> getName()));

    }


    /*
    *  Create method that takes a form with list of names and add to db
    *  @Rest\Post("/session")
    */
    public function createTeaMakerSession(Request $request)
    {
        $participant=Participant();
        $form=$this->createForm(ParticipantForm::class,$Participant);
        $data=json_decode($request->getContent(),true);
        $form->submit($data);
        if($form->isSubmitted()&&$form->isValid())
        {

            //psuedo code 

            if(partipants == null)
            {
                return $this->handleView($this->view(['status'=>'error'],Response::HTTP_BAD_REQUEST));
            }

            $sessionId = addParticpantToSession($data)

            if($sessionId == null)
            {
                return $this->handleView($this->view(['status'=>'error'],Response::HTTP_BAD_REQUEST));
            }

            
            return$this->handleView($this->view($session->getId());
        }
        return$this->handleView($this->view($form->getErrors()));
    }


    private ParticpantToSession addParticpantToSession(ArrayCollection partipants)
    {
        

        $newPartToSession = new ParticpantToSession();
        $teaSession = new TeaMakerSession();
        $teaSession ->setParticipantToSessionId($newPartToSession->getId())
        $em=$this->getDoctrine()->getManager();
        $em->persist($teaSession);

        // idea here is given array of  name, create particpant entities,
            tie those partipant to a tea session via the join table

        for user in partipant
        {
            $participant = new Participant()
            $participant->name = user->name;
            $participant->ParticipantToSessionId = $newPartToSession->getId()
           
            $newPartToSession->setPartipantId($participant->getId())
            $newPartToSession->teaMakerSessionId($teaSession->getId())
            
            $em->persist($participant);
            $em->persist($newPartToSession);    
        }

        $em->flush();
        $em->clear();

        return newPartToSession;
    }




}